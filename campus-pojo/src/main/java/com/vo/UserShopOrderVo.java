package com.vo;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class UserShopOrderVo {
    private Integer id;
    private Integer userId;
    private Integer status;
    private double money;
    private String addressEnd;
    private String Commodities;
    private String notes;
}
