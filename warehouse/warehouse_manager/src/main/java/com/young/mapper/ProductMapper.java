package com.young.mapper;

import com.young.page.Page;
import com.young.pojo.Product;
import com.young.vo.Statistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    //查询商品总行数的方法
    public int selectProductCount(Product product);

    //分页查询商品的方法
    public List<Product> selectProductPage(@Param("page") Page page,
                                           @Param("product") Product product);
    //添加商品的方法
    public int insertProduct(Product product);
    //根据商品id修改商品的上下架状态
    public int updateStateById(Product product);
    //根据商品id删除商品的方法
    public int deleteProductByIds(List<Integer>  productId);
    //根据商品id修改商品的方法
    public int updateProductById(Product product);
    //根据商品id增加商品库存的方法
    public int addInventById(Integer productId, Integer invent);
    //根据商品id查询商品的方法
    public Product selectProductById(Integer productId);
    //统计各个仓库商品库存数量的方法
    public List<Statistics> statisticsStoreInvent();

}