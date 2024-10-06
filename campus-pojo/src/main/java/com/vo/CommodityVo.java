package com.vo;

import lombok.Data;

@Data
public class CommodityVo {
    private int id;
    private String commodityName;
    private double price;
    private double weight;
    private String photoPath;
    private int status;
    private int commodityClassId;
}
