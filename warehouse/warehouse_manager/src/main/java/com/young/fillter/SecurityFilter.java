package com.young.fillter;

import com.alibaba.fastjson.JSON;
import com.young.vo.Result;
import com.young.vo.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 登录限制的Servlet过滤器:
 */

public class SecurityFilter implements Filter {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void doFilter(ServletRequest Request, ServletResponse Response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)Request;
        HttpServletResponse response=(HttpServletResponse) Response;
        //获取请求url接口
        String path = request.getServletPath();

        /*设置白名单，放行*/
        ArrayList<Object> list = new ArrayList<>();
        list.add("/captcha/captchaImage");
        list.add("/login");
        list.add("/logout");
        list.add("/product/img-upload");
        if (list.contains(path) || path.contains("/img/upload")) {
            filterChain.doFilter(request, response);
            return;
        }
         /*
          其它请求都校验token:
         */
        //拿到前端归还的token
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        if (StringUtils.hasText(token) && stringRedisTemplate.hasKey(token)) {
            filterChain.doFilter(request,response);
            return;
        }
        //token校验失败,向前端响应失败的Result对象转成的json串
        Result err = Result.err(502, "你的账户未登录");
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        String s = JSON.toJSONString(err);
        writer.print(s);
        writer.flush();
        writer.close();

    }


}
