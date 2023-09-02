package com.young.controller;

import com.young.page.Page;
import com.young.pojo.BuyList;
import com.young.pojo.CurrentUser;
import com.young.pojo.InStore;
import com.young.pojo.Store;
import com.young.service.BuyListService;
import com.young.service.InStoreService;
import com.young.service.StoreService;
import com.young.utils.TokenUtils;
import com.young.vo.Result;
import com.young.vo.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/purchase")
@RestController
public class BuyListController {
    @Autowired
    private BuyListService buyListService;
    //注入TokenUtils
    @Autowired
    private TokenUtils tokenUtils;
    //注入InStoreService
    @Autowired
    private InStoreService inStoreService;

    /**
     * 添加采购单的url接口/purchase/purchase-add
     */
    @RequestMapping("/purchase-add")
    public Result addPurchase(@RequestBody BuyList buyList){
        //执行业务
        Result result = buyListService.savePurchase(buyList);
        //响应
        return result;
    }
    //注入StoreService
    @Autowired
    private StoreService storeService;

    /**
     * 查询所有仓库的url接口/purchase/store-list
     */
    @RequestMapping("/store-list")
    public Result storeList(){
        //执行业务
        List<Store> storeList = storeService.queryAllStore();
        //响应
        return Result.ok(storeList);
    }
    @RequestMapping("/purchase-page-list")
    public Result purchasePageList(Page page, BuyList buyList){
        //执行业务
        page = buyListService.queryPurchasePage(page, buyList);
        //响应
        return Result.ok(page);
    }
    /**
     * 删除采购单的url接口/purchase/purchase-delete/{buyId}
     *
     * @PathVariable Integer buyId将路径占位符buyId的值赋值给参数变量buyId;
     */
    @RequestMapping("/purchase-delete/{buyId}")
    public Result deletePurchase(@PathVariable Integer buyId){
        //执行业务
        Result result = buyListService.deletePurchase(buyId);
        //响应
        return result;
    }
    @RequestMapping("/purchase-update")
    public Result updatePurchase(@RequestBody BuyList buyList){
        //执行业务
        Result result = buyListService.updatePurchase(buyList);
        //响应
        return result;
    }
    @RequestMapping("/in-warehouse-record-add")
    public Result addInStore(@RequestBody BuyList buyList,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id -- 创建入库单的用户id
        int createBy = currentUser.getUserId();

        //创建InStore对象封装添加的入库单的信息
        InStore inStore = new InStore();
        inStore.setStoreId(buyList.getStoreId());
        inStore.setProductId(buyList.getProductId());
        inStore.setInNum(buyList.getFactBuyNum());
        inStore.setCreateBy(createBy);

        //执行业务
        Result result = inStoreService.saveInStore(inStore, buyList.getBuyId());

        //响应
        return result;
    }

}
