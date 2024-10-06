package com.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;
@ApiModel
@Data
public class CommodityPageQueryDto {

    private int type;

    private int page;

    private int pageSize;
}
