package com.young.service.impl;

import com.young.pojo.Brand;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.young.mapper.BrandMapper;
import com.young.service.BrandService;

import java.util.List;
//指定缓存的名称即键的前缀,一般是@CacheConfig标注的类的全类名
@CacheConfig(cacheNames = "com.young.service.impl.BrandServiceImpl")
@Service
public class BrandServiceImpl implements BrandService{

    @Resource
    private BrandMapper brandMapper;

    /*
      查询所有品牌的业务方法
     */
    //对查询到的所有品牌进行缓存,缓存到redis的键为all:brand
    @Cacheable(key = "'all:brand'")
    @Override
    public List<Brand> queryAllBrand() {
        //查询所有品牌
        return brandMapper.findAllBrand();
    }

}
