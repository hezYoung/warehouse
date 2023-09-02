package com.young.controller;


import com.young.page.Page;
import com.young.pojo.InStore;
import com.young.pojo.Store;
import com.young.service.InStoreService;
import com.young.service.StoreService;
import com.young.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/instore")
@RestController
public class InStoreController {

    //注入StoreService
    @Autowired
    private StoreService storeService;
    @Autowired
    private InStoreService inStoreService;

    /**
     * 查询所有仓库的url接口/instore/store-list
     */
    @RequestMapping("/store-list")
    public Result storeList(){
        //执行业务
        List<Store> storeList = storeService.queryAllStore();
        //响应
        return Result.ok(storeList);
    }
    /**
     * 分页查询入库单的url接口/instore/instore-page-list
     *
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数InStore对象用于接收请求参数仓库id storeId、商品名称productName、
     * 起止时间startTime和endTime;
     *
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @RequestMapping("/instore-page-list")
    public Result inStorePageList(Page page, InStore inStore){
        //执行业务
        page = inStoreService.queryInStorePage(page, inStore);
        //响应
        return Result.ok(page);
    }
    /**
     * 确定入库的url接口/instore/instore-confirm
     *
     * @RequestBody InStore inStore将请求传递的json数据封装到参数InStore对象;
     */
    @RequestMapping("/instore-confirm")
    public Result confirmInStore(@RequestBody InStore inStore){
        //执行业务
        Result result = inStoreService.confirmInStore(inStore);
        //响应
        return result;
    }

}
