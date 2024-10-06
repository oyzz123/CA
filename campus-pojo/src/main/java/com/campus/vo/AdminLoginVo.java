package com.campus.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "管理员登录返回的数据格式")
public class AdminLoginVo implements Serializable {

    @ApiModelProperty("主键值")
    private int id;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("昵称")
    private String name;

    @ApiModelProperty("jwt令牌")
    private String token;

}
