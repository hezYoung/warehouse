package com.young.service;

import com.young.pojo.ProductType;
import com.young.vo.Result;

import java.util.List;

public interface ProductTypeService{
    //查询所有商品分类树的业务方法
    public List<ProductType> allProductTypeTree();

    //校验分类编码是否已存在的业务方法
    public Result queryTypeByCode(String typeCode);

    //添加商品分类的业务方法
    public Result saveProductType(ProductType productType);
    //删除商品分类的业务方法
    public Result removeProductType(Integer typeId);
    //修改商品分类的业务方法
    public Result updateProductType(ProductType productType);

}
