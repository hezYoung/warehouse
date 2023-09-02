package com.young.service.impl;

import com.young.pojo.Supply;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.young.mapper.SupplyMapper;
import com.young.service.SupplyService;

import java.util.List;
//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@CacheConfig(cacheNames = "com.young.service.impl.SupplyServiceImpl")
@Service
public class SupplyServiceImpl implements SupplyService{

    @Resource
    private SupplyMapper supplyMapper;
    @Cacheable(key = "'all:supply'")
    @Override
    public List<Supply> queryAllSupply() {
        //查询所有供应商
        return supplyMapper.findAllSupply();
    }
}
