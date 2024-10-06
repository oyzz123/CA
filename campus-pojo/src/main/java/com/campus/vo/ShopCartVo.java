package com.campus.vo;

import lombok.Data;

@Data
public class ShopCartVo {
    private Integer userId;
    private CommodityVo commodity;
    private Integer num;
}
