package com.campus.dto;

import lombok.Data;

@Data
public class ShopOrderDto {
    private Integer userId;
    private double money;
    private String addressEnd;
    private String commodities;
    private String notes;
}
