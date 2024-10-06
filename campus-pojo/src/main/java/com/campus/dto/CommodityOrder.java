package com.campus.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class CommodityOrder {

    private Integer commodityId;

    private Integer price;

    private Integer num;

}
