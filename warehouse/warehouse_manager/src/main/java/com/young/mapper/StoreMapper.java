package com.young.mapper;

import com.young.pojo.Store;

import java.util.List;

public interface StoreMapper {
    //查询所有仓库的方法
    public List<Store> findAllStore();

}