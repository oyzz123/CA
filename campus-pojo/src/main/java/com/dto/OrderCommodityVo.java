package com.dto;

import lombok.Data;

@Data
public class OrderCommodityVo {
    private int userId;
    private int commodityId;
    private int typeId;
    private int num;
}
