package com.young.service;

import com.young.dto.AssignRoleDto;
import com.young.page.Page;
import com.young.pojo.User;
import com.young.vo.Result;

public interface UserService {
    public User findUserByCode(String userCode);
    //分页查询用户的业务方法
    Page findUserPage(Page page, User user);

    //新增用户
    Result saveallUser(User user);

    Result setStateto(User user);

    void assignRole(AssignRoleDto assignRoleDto);

    int setUserDelete(Integer userId);

    Result updateNameById(User user);

    //修改密码
    Result setPwdById(Integer userId);
}
