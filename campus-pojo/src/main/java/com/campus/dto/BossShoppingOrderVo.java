package com.campus.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("老板分页查询超市订单传递数据模型")
public class BossShoppingOrderVo {
    int page;
    int pagesize;
}
