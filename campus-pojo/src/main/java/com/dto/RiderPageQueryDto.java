package com.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class RiderPageQueryDto {
    private int id;

    private int page;

    private int pageSize;

}