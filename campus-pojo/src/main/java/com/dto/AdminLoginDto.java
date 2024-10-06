package com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel( description = "管理员登录数据")
public class AdminLoginDto {
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("密码")
    private String password;
}
