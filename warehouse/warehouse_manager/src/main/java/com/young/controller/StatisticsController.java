package com.young.controller;

import com.young.service.ProductService;
import com.young.vo.Result;
import com.young.vo.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/statistics")
@RestController
public class StatisticsController {

    //注入StatisticsService
    @Autowired
    private ProductService statisticsService;

    /**
     * 统计各个仓库商品库存数量的url接口/statistics/store-invent
     */
    @RequestMapping("/store-invent")
    public Result statisticsStoreInvent(){
        //执行业务
        List<Statistics> statisticsList = statisticsService.statisticsStoreInvent();
        //响应
        return Result.ok(statisticsList);
    }

}
