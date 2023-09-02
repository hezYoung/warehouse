package com.young.controller;

import com.google.code.kaptcha.Producer;
import com.young.pojo.Auth;
import com.young.pojo.CurrentUser;
import com.young.pojo.LoginUser;
import com.young.pojo.User;
import com.young.service.AuthService;
import com.young.service.UserService;
import com.young.utils.DigestUtil;
import com.young.utils.TokenUtils;
import com.young.vo.Result;
import com.young.vo.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
@RestController
public class LoginController {
    @Resource(name = "captchaProducer")
    private Producer producer;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserService loginService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private AuthService authService;

    @GetMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        //生成文本
        String text = producer.createText();
        //生成图片
        BufferedImage image = producer.createImage(text);
        //存入redis
        redisTemplate.opsForValue().set(text, "", 60 * 5, TimeUnit.SECONDS);
        //传入前端
        //响应正文为jpg图片即验证码图片
        response.setContentType("image/jpeg");
        out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();

    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginUser loginUser) {

//        校验验证码：

        if (!redisTemplate.hasKey(loginUser.getVerificationCode())) {
            return Result.err(Result.CODE_ERR_BUSINESS, "验证码不正确！");
        }

        User userByCode = loginService.findUserByCode(loginUser.getUserCode());
        if (userByCode != null) {
            if (userByCode.getUserState().equals(WarehouseConstants.USER_STATE_PASS)) {//查到的用户状态是已审核
                String password = DigestUtil.hmacSign(loginUser.getUserPwd());
                if (password.equals(userByCode.getUserPwd())) {
                    //密码相匹配
                    CurrentUser currentUser = new CurrentUser(userByCode.getUserId(), userByCode.getUserCode(), userByCode.getUserName());
                    String token = tokenUtils.loginSign(currentUser, userByCode.getUserPwd());
                    return Result.ok("用户登录成功", token);
                } else {
                    return Result.ok("用户密码错误");
                }
            } else {
                return Result.ok("用户没有审核");
            }
        }
        return Result.ok("用户不存在");

    }

    @GetMapping("/curr-user")
    public Result curruser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        return Result.ok(currentUser);
    }

    @GetMapping("/user/auth-list")
    private Result alltree(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        List<Auth> auths = authService.findbyAuth(userId);
        return Result.ok(auths);
    }
    /**
     * 登出的url接口/logout
     *
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String clientToken
     * 将请求头Token的值即前端归还的token,赋值给请求处理方法的参数String clientToken
     */
    @DeleteMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)
                         String clientToken) {
        //从redis移除token
        redisTemplate.delete(clientToken);
        return Result.ok();
    }
}
