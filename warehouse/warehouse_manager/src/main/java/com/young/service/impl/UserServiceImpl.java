package com.young.service.impl;

import com.young.dto.AssignRoleDto;
import com.young.mapper.RoleMapper;
import com.young.mapper.UserMapper;
import com.young.mapper.UserRoleMapper;
import com.young.page.Page;
import com.young.pojo.User;
import com.young.service.UserService;
import com.young.utils.DigestUtil;
import com.young.vo.Result;
import com.young.vo.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    //注入UserMapper
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    //根据用户名查找用户的业务方法
    @Override
    public User findUserByCode(String userCode) {

        User findbyuser = userMapper.findbyuser(userCode);
        return findbyuser;
    }
    //分页查询
    @Override
    public Page findUserPage(Page page, User user) {
        //查询用户总行数
        int userCount = userMapper.selectUserCount(user);

        //分页查询用户
        List<User> userList = userMapper.selectUserPage(page, user);

        //将查询到的总行数和当前页数据组装到Page对象
        page.setTotalNum(userCount);
        page.setResultList(userList);

        return page;

    }

    @Override
    public Result saveallUser(User user) {
        User oldUser = userMapper.findbyuser(user.getUserCode());
        if(oldUser!=null){//用户已存在
            return Result.err(Result.CODE_ERR_BUSINESS, "该用户已存在！");
        }

        String userPwd  = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(userPwd );
        userMapper.saveallUser(user);
        return Result.ok("添加用户成功！");
    }

    @Override
    public Result setStateto(User user) {
        //根据用户id修改用户状态
        int i = userMapper.updateUserState(user);
        if(i>0){
            return Result.ok("修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！");

    }

    @Override
    public void assignRole(AssignRoleDto assignRoleDto) {
        userRoleMapper.deleteRolebyid(assignRoleDto.getUserId());
        List<String> roleCheckList = assignRoleDto.getRoleCheckList();
        for (String roleName  : roleCheckList) {
            //根据当前角色名查询当前角色的id
            int idByName = userRoleMapper.getRoleIdByName(roleName);
            userRoleMapper.insertUserRole(assignRoleDto.getUserId(),idByName);

        }
    }

    @Override
    public int setUserDelete(Integer userId) {
        int userDelete = userMapper.setUserDelete(userId);
        return userDelete;
    }

    @Override
    public Result updateNameById(User user) {
        int i = userMapper.updateNameById(user);
        if (i > 0) {
            return Result.ok("用户修改成功");
        }
        return Result.err(500,"用户修改失败");
    }

    @Override
    public Result setPwdById(Integer userId) {
        String s = DigestUtil.hmacSign("123456");
        int i = userMapper.updatePwdById(userId, s);
        if (i > 0) {
            return Result.ok("重置成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"重置失败");
    }


}
