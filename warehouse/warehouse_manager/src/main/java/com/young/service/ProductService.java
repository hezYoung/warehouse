package com.young.service;

import com.young.page.Page;
import com.young.pojo.Product;
import com.young.vo.Result;
import com.young.vo.Statistics;

import java.util.List;

public interface ProductService{
    //分页查询商品的业务方法
    public Page queryProductPage(Page page, Product product);
    //添加商品的业务方法
    public Result saveProduct(Product product);
    //修改商品上下架状态的业务方法
    public Result updateProductState(Product product);

    public Result delGoodsbyId(List<Integer> proidList);
    //修改商品的业务方法
    public Result updateProduct(Product product);
    //统计各个仓库商品库存数量的业务方法
    public List<Statistics> statisticsStoreInvent();

}
