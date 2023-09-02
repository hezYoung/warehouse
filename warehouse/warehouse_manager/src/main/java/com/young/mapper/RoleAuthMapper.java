package com.young.mapper;

import com.young.dto.AssignAuthDto;

import java.util.List;

public interface RoleAuthMapper {
    int removeRoleid(Integer roleId);

    List<Integer> findallIds(Integer roleId);

    int insertAuth(Integer roleId, Integer authId);
    public int delAuthByRoleId(Integer roleId);
}