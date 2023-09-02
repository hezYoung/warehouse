package com.young.service;

import com.young.page.Page;
import com.young.pojo.OutStore;
import com.young.vo.Result;

public interface OutStoreService{
    //添加出库单的业务方法
    public Result saveOutStore(OutStore outStore);
    //分页查询出库单的业务方法
    public Page outStorePage(Page page, OutStore outStore);
    //确定出库的业务方法
    public Result confirmOutStore(OutStore outStore);

}
