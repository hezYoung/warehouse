package com.young.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.young.mapper.RoleAuthMapper;
import com.young.service.RoleAuthService;
@Service
public class RoleAuthServiceImpl implements RoleAuthService{

    @Resource
    private RoleAuthMapper roleAuthMapper;

}
