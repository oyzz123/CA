package com.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class ClassificationVo {
    private Integer id;

    private String name;
}
