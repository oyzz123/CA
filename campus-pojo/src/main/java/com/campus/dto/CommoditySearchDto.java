package com.campus.dto;

import lombok.Data;

@Data
public class CommoditySearchDto {
    private int page;
    private int pageSize;
    private String commodityName;
}
