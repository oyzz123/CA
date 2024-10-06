package com.campus.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class CommodityRefundDto {

    private String commodityName;

    private String price;

    private Integer allowance;

    private String weight;

    private String photoPath;

    private Integer status;
}
