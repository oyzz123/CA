package com.entity;

import lombok.Data;

@Data
public class OrderCommodity {
    private int userId;
    private int commodityId;
    private double price;
    private int num;
}
