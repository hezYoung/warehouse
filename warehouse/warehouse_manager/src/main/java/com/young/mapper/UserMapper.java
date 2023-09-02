package com.young.mapper;

import com.young.page.Page;
import com.young.pojo.User;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface UserMapper {
    User findbyuser(String userCode);
    //查询总行数
     int selectUserCount(User user);

    //分页查询用户的方法
    public List<User> selectUserPage(@Param("page") Page page, @Param("user") User user);
//插入
    int saveallUser(User user);
    //根据用户id修改用户状态的方法
    public int updateUserState(User user);

    int setUserDelete(Integer userId);
//修改用户
    int updateNameById(User user);

    int updatePwdById(@Param("userId") Integer userId,@Param("userPwd") String password);
}
