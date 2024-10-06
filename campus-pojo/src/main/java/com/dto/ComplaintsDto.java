package com.dto;

import lombok.Data;

@Data
public class ComplaintsDto {
    private Integer userId;
    private Integer orderId;
    private String message;
}
