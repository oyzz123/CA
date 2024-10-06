package com.campus.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VoucherDto {
    private Integer id;
    //支付金额
    private Double payMoney;
    //满减金额
    private Double enoughMoney;
    //优惠券金额
    private Double money;
    //库存
    private Integer num;
    private Integer type;
    private Integer status;
    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    //过期时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;
}
