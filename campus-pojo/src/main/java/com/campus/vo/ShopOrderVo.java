package com.campus.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopOrderVo {
    private Integer id;
    private String commodities;
    private String addressEnd;
    private double money;
    private int status;

    private String notes;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String nickname;
    private String telephone;
}
