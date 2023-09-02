package com.young.service.impl;

import com.young.mapper.ProductMapper;
import com.young.page.Page;
import com.young.pojo.OutStore;
import com.young.pojo.Product;
import com.young.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.young.mapper.OutStoreMapper;
import com.young.service.OutStoreService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutStoreServiceImpl implements OutStoreService{

    @Resource
    private OutStoreMapper outStoreMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Result saveOutStore(OutStore outStore) {
        //添加出库单
        int i = outStoreMapper.insertOutStore(outStore);
        if(i>0){
            return Result.ok("添加出库单成功！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "添加出库单失败！");

    }

    @Override
    public Page outStorePage(Page page, OutStore outStore) {
        //查询出库单总行数
        int outStoreCount = outStoreMapper.outStoreCount(outStore);

        //分页查询出库单
        List<OutStore> outStoreList = outStoreMapper.outStorePage(page, outStore);

        //将查询到的总行数和当前页数据封装到Page对象
        page.setTotalNum(outStoreCount);
        page.setResultList(outStoreList);
        return page;

    }
    //确定出库的业务方法
    @Transactional//事务处理
    @Override
    public Result confirmOutStore(OutStore outStore) {
        //根据商品id查询商品
        Product product = productMapper.selectProductById(outStore.getProductId());
        if(outStore.getOutNum()>product.getProductInvent()){
            return Result.err(Result.CODE_ERR_BUSINESS, "商品库存不足");
        }

        //根据id将出库单状态改为已出库
        int i = outStoreMapper.updateIsOutById(outStore.getOutsId());
        if(i>0){
            //根据商品id减商品库存
            int j = productMapper.addInventById(outStore.getProductId(),
                    -outStore.getOutNum());
            if(j>0){
                return Result.ok("出库成功！");
            }
            return Result.err(Result.CODE_ERR_BUSINESS, "出库失败！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "出库失败！");
    }
}

