package com.campus.dto;

import lombok.Data;

@Data
public class RiderRefundOrderDto {
    private Integer riderId;
    private Integer orderId;
    private String message;
}
