package com.young.mapper;


import com.young.page.Page;
import com.young.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 15455
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2023-08-20 15:35:26
* @Entity com.young.pojo.Role
*/
public interface RoleMapper {
    //查询状态正常的所有角色的方法
    public List<Role> findAllRole();

    //根据用户id查询用户已分配的角色
    public List<Role> findRolesByUserId(Integer userId);

    //查询条数
    public int findCount(Role role);

    //分页查询
    public List<Role> findallRolePage(@Param("page") Page page, @Param("role") Role role);
    //查询是否有这个角色
    public Role findRoleByNameOrCode(String roleName, String roleCode);

    //添加角色方法
    public int insertRole(Role role);
    //根据角色id修改角色状态的方法
    public int updateRoleState(Role role);

    //删除用户
    int deleteByRoleIdInt(Integer roleId);

    //根据角色id修改角色描述的方法
    public int updateDescById(Role role);

}




