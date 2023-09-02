package com.young.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统计实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    private Integer storeId;//仓库id

    private String storeName;//仓库名称

    private Integer totalInvent;//仓库商品库存数
}
