package com.young.service;

import com.young.dto.AssignAuthDto;
import com.young.page.Page;
import com.young.pojo.Role;
import com.young.pojo.User;
import com.young.vo.Result;

import java.util.List;


/**
* @author 15455
* @description 针对表【role(角色表)】的数据库操作Service
* @createDate 2023-08-20 15:35:26
*/
public interface RoleService {
    List<Role> getAllRole();
    //查询用户已分配的角色的业务方法
    public List<Role> queryRolesByUserId(Integer userId);

    //分页查询
    public Page queryRolePage(Page page, Role role);

    //添加角色
    public Result insertRole(Role role);

    Result updateRoleState(Role role);

    Result deleteRolebyId(Integer roleId);

    List<Integer> findallIds(Integer roleId);

    void insertAuth(AssignAuthDto assignAuthDto);

    //修改角色描述的业务方法
    public Result updateRoleDesc(Role role);

}
