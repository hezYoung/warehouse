package com.young.mapper;

import com.young.pojo.Supply;

import java.util.List;

public interface SupplyMapper {
    //查询所有供应商的方法
    public List<Supply> findAllSupply();

}