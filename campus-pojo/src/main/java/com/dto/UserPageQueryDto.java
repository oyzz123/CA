package com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
@ApiModel
public class UserPageQueryDto {

    private Integer page;

    private Integer pageSize;
}