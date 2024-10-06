package com.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "管理员添加老板传递数据模型")
public class AdminBossSaveDto {
    @ApiModelProperty("主键值")
    private int id;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("性别")
    private String gender;
    @ApiModelProperty("年龄")
    private int age;
    @ApiModelProperty("联系方式")
    private String telephone;

}
