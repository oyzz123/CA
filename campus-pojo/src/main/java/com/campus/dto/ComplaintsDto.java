package com.campus.dto;

import lombok.Data;

@Data
public class ComplaintsDto {
    private Integer userId;
    private Integer orderId;
    private String message;
}
