package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vo.CommodityVo;
import io.swagger.models.auth.In;
import lombok.Data;

import java.time.LocalDateTime;

//TODO shopcart
@Data
public class ShopCart {
    private CommodityVo commodity;
    private Integer num;
    private Integer check;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
