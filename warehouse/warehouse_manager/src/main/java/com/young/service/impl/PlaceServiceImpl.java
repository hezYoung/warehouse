package com.young.service.impl;

import com.young.pojo.Place;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.young.mapper.PlaceMapper;
import com.young.service.PlaceService;

import java.util.List;
//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@CacheConfig(cacheNames = "com.young.service.impl.PlaceServiceImpl")
@Service
public class PlaceServiceImpl implements PlaceService{

    @Resource
    private PlaceMapper placeMapper;
    @Cacheable(key = "'all:place'")
    @Override
    public List<Place> queryAllPlace() {
        //查询所有产地
        return placeMapper.findAllPlace();

    }
}
