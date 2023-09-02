package com.young.mapper;

import com.young.page.Page;
import com.young.pojo.BuyList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BuyListMapper {
    //添加采购单的方法
    public int insertPurchase(BuyList buyList);
    //查询采购单总行数的方法
    public int selectPurchaseCount(BuyList buyList);

    //分页查询采购单的方法
    public List<BuyList> selectPurchasePage(@Param("page") Page page,
                                            @Param("purchase") BuyList buyList);
    //根据id删除采购单的方法
    public int deletePurchaseById(Integer buyId);
    //根据id修改采购单的方法
    public int updatePurchaseById(BuyList buyList);
    //根据id将采购单状态改为已入库的方法
    public int updateIsInById(Integer buyId);

}