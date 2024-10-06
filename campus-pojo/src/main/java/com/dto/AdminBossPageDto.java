package com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "管理员分页查询老板传递数据模型")
public class AdminBossPageDto implements Serializable {
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("页码")
    private int page;
    @ApiModelProperty("每页记录数")
    private int pageSize;
}
