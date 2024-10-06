package com.dto;

import lombok.Data;

//TODO shopcardto
@Data
public class ShopCartDto {
    private Integer userId;
    private Integer commodityId;
    private Integer num;
    private Integer check;
}
