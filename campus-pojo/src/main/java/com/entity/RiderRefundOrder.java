package com.entity;

import lombok.Data;

@Data
public class RiderRefundOrder {
    private Integer riderId;
    private Integer orderId;
    private String notes;
}
