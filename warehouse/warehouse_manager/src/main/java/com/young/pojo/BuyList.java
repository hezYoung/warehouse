package com.young.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 采购单
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyList implements Serializable {
    private Integer buyId;

    private Integer productId;

    private Integer storeId;

    private Integer buyNum;

    private Integer factBuyNum;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date buyTime;

    private Integer supplyId;

    private Integer placeId;

    private String buyUser;

    private String phone;

    /**
    * 0 否 1 是
    */
    private String isIn;

    //---------------追加属性---------------------------

    private String productName;//商品名称

    private String storeName;//仓库名称

    private String startTime;//搜索起始时间

    private String endTime;//搜索结束时间

}