package com.young.service.impl;

import com.young.pojo.Store;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.young.mapper.StoreMapper;
import com.young.service.StoreService;

import java.util.List;

@Service
//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@CacheConfig(cacheNames = "com.young.service.impl.StoreServiceImpl")
public class StoreServiceImpl implements StoreService{

    @Resource
    private StoreMapper storeMapper;
    /*
         查询所有仓库的业务方法
        */
    //对查询到的所有仓库进行缓存,缓存到redis的键为all:store
    @Cacheable(key = "'all:store'")
    @Override
    public List<Store> queryAllStore() {
        List<Store> allStore = storeMapper.findAllStore();
        return allStore;
    }
}
