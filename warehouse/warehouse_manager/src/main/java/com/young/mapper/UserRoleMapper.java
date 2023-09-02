package com.young.mapper;

import com.young.pojo.UserRole;


/**
* @author 15455
* @description 针对表【user_role(用户角色表)】的数据库操作Mapper
* @createDate 2023-08-20 15:32:39
* @Entity com.young.pojo.UserRole
*/
public interface UserRoleMapper {
    int deleteRolebyid(Integer userId);
    //添加用户角色关系的方法
    public void insertUserRole(Integer userId, Integer roleId);
    //根据角色名称查询角色id
    public int getRoleIdByName(String roleName);

}




