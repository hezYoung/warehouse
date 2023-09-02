package com.young.service;

import com.young.page.Page;
import com.young.pojo.BuyList;
import com.young.vo.Result;

public interface BuyListService{
    //添加采购单的业务方法
    public Result savePurchase(BuyList buyList);
    //分页查询采购单的业务方法
    public Page queryPurchasePage(Page page, BuyList buyList);
    //删除采购单的业务方法
    public Result deletePurchase(Integer buyId);
    //修改采购单的业务方法
    public Result updatePurchase(BuyList buyList);

}
