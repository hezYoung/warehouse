package com.young.service.impl;

import com.young.dto.AssignAuthDto;
import com.young.mapper.RoleAuthMapper;
import com.young.page.Page;
import com.young.pojo.Role;
import com.young.pojo.User;
import com.young.service.RoleService;
import com.young.mapper.RoleMapper;
import com.young.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15455
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2023-08-20 15:35:26
*/
@Service
@CacheConfig(cacheNames = "com/young/service/impl/RoleServiceImpl")
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Cacheable(key = "'all:role'")
    @Override
    public List<Role> getAllRole() {
        List<Role> allRole = roleMapper.findAllRole();
        return allRole;
    }

    @Override
    public List<Role> queryRolesByUserId(Integer userId) {
        return roleMapper.findRolesByUserId(userId);
    }

    @Override
    public Page queryRolePage(Page page, Role role) {
        int roleCount  = roleMapper.findCount(role);
        List<Role> roleList  = roleMapper.findallRolePage(page, role);
        page.setTotalNum(roleCount);
        page.setResultList(roleList );

        return page;
    }
    @CacheEvict(key = "'all:role'")
    @Override
    public Result insertRole(Role role) {
        int i = roleMapper.insertRole(role);
        if (i > 0) {
            return Result.ok("插入成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "插入失败");
    }
    @CacheEvict(key = "'all:role'")
    @Override
    public Result updateRoleState(Role role) {
        int i = roleMapper.updateRoleState(role);

        if (i > 0) {
            return Result.ok("状态修改成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "状态修改失败");
    }
    @CacheEvict(key = "'all:role'")
    @Override
    public Result deleteRolebyId(Integer roleId) {
        int i = roleMapper.deleteByRoleIdInt(roleId);
        if (i > 0) {
            roleAuthMapper.removeRoleid(roleId);
            return Result.ok("角色删除成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色删除失败");
    }

    @Override
    public List<Integer> findallIds(Integer roleId) {
        List<Integer> findallIds = roleAuthMapper.findallIds(roleId);
        return findallIds;
    }

    @Override
    public void insertAuth(AssignAuthDto assignAuthDto) {
        //拿到角色id
        Integer roleId = assignAuthDto.getRoleId();
        roleAuthMapper.delAuthByRoleId(roleId);
        List<Integer> authIds = assignAuthDto.getAuthIds();
        for (Integer authId : authIds) {
            roleAuthMapper.insertAuth(roleId, authId);
        }


    }

    @Override
    public Result updateRoleDesc(Role role) {
        int i = roleMapper.updateDescById(role);
        if(i>0){
            return Result.ok("角色修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "角色修改失败！");

    }


}




