package com.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("管理员查询总流水返回数据模型")
public class AdminQueryVo {
    @ApiModelProperty("月份")
    int month;
    @ApiModelProperty("超市")
    String type1;
    @ApiModelProperty("超市使用人数")
    Integer shopUsers;
    @ApiModelProperty("超市成交订单数")
    Integer shopOrders;
    @ApiModelProperty("超市流水")
    Double shopMoney=0.0;
    @ApiModelProperty("跑腿")
    String type2;
    @ApiModelProperty("跑腿使用人数")
    Integer takeawayUsers;
    @ApiModelProperty("跑腿成交订单数")
    Integer takeawayOrders;
    @ApiModelProperty("跑腿流水")
    Double takeawayMoney=0.0;
    @ApiModelProperty("总人数")
    Integer sumUsers;
    @ApiModelProperty("总订单数")
    Integer sumOrders;
    @ApiModelProperty("总流水")
    Double sumMoney =0.0;

    @ApiModelProperty("盈利")
    Double profit=0.0;
}
