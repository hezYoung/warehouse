package com.young.service.impl;

import com.young.pojo.Unit;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.young.mapper.UnitMapper;
import com.young.service.UnitService;

import java.util.List;

@Service
@CacheConfig(cacheNames = "com.young.service.impl.UnitServiceImpl")
public class UnitServiceImpl implements UnitService {

    @Resource
    private UnitMapper unitMapper;

    /*
      查询所有单位的业务方法
     */
    //对查询到的所有单位进行缓存,缓存到redis的键为all:unit
    @Cacheable(key = "'all:unit'")
    @Override
    public List<Unit> queryAllUnit() {
        //查询所有单位
        return unitMapper.findAllUnit();
    }
}

