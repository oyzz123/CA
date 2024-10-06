package com.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
public class CommodityDto {

    private Integer id;

    private Integer commodityClassId;

    private String commodityName;

    private String price;

    private Integer allowance;

    private String weight;

    private String photoPath;

    private Integer status;
}
