package com.campus.dto;

import lombok.Data;

@Data
public class RefundOrderDto {
    private Integer userId;
    private Integer orderId;
    private String message;
}
