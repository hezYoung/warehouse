package com.young.service.impl;

import com.young.page.Page;
import com.young.pojo.BuyList;
import com.young.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.young.mapper.BuyListMapper;
import com.young.service.BuyListService;

import java.util.List;

@Service
public class BuyListServiceImpl implements BuyListService{

    @Autowired
    private BuyListMapper buyListMapper;

    @Override
    public Result savePurchase(BuyList buyList) {
        //添加采购单
        int i = buyListMapper.insertPurchase(buyList);
        if(i>0){
            return Result.ok("采购单添加成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "采购单添加失败！");
    }

    @Override
    public Page queryPurchasePage(Page page, BuyList buyList) {
        //查询采购单总行数
        int purchaseCount = buyListMapper.selectPurchaseCount(buyList);

        //分页查询采购单
        List<BuyList> purchaseList = buyListMapper.selectPurchasePage(page, buyList);

        //将查询到的总行数和当前页数据组装到Page对象
        page.setTotalNum(purchaseCount);
        page.setResultList(purchaseList);

        return page;

    }

    @Override
    public Result deletePurchase(Integer buyId) {

        int i = buyListMapper.deletePurchaseById(buyId);
        if(i>0){
            return Result.ok("采购单删除成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "采购单删除失败！");

    }

    @Override
    public Result updatePurchase(BuyList buyList) {
        //根据id修改采购单
        int i = buyListMapper.updatePurchaseById(buyList);
        if(i>0){
            return Result.ok("采购单修改成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "采购单修改失败！");
    }
}


