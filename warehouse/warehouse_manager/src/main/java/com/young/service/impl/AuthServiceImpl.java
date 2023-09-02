package com.young.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.young.mapper.AuthMapper;
import com.young.pojo.Auth;
import com.young.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Service
@CacheConfig(cacheNames = "com/young/service/impl/RoleServiceImpl")
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public List<Auth> findbyAuth(int userId) {
        /*向redis里面查询权限的缓存记录*/
        String authTree = redisTemplate.opsForValue().get("authTree:" + userId);
        if (StringUtils.hasText(authTree)) {
            //查到就将json转换成list返回
            List<Auth> authList = JSON.parseArray(authTree, Auth.class);
            return authList;

        }else {
            //没有查到，就生成权限树，然后存入redis
            List<Auth> allAuthList = authMapper.findbyAuth(userId);
            //【parentId最初为0,即最初查的是所有一级权限(菜单)】，调用方法
            List<Auth> authTreeList = allAuthTree(allAuthList, 0);
            //存入redis
            redisTemplate.opsForValue().set("authTree:" + userId,JSON.toJSONString(authTreeList));
            return authTreeList;
        }




    }

    //查询出来所有权限
    @Cacheable(key = "'all:auth'")
    @Override
    public List<Auth> findAuthTree() {
        List<Auth> authTree = authMapper.findAuthTree();
        List<Auth> authList = allAuthTree(authTree, 0);
        return authList;
    }

    /*使用递归来一层一层查询权限tree*/
    private List<Auth> allAuthTree(List<Auth> auths, int parentId) {
        //获取父权限(菜单)id为参数parentId的所有权限(菜单)
        //【parentId最初为0,即最初查的是所有一级权限(菜单)】
        List<Auth> firsttree = new ArrayList<>();
        for (Auth auth : auths) {
            if (auth.getParentId() == parentId) {
                firsttree.add(auth);
            }
        }
        //查询第二层
        for (Auth auth : firsttree) {
            List<Auth> child = allAuthTree(auths,auth.getAuthId());
            auth.setChildAuth(child);
        }
        return firsttree;
    }
}
