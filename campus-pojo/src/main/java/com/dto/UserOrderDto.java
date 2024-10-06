package com.dto;

import lombok.Data;

@Data
public class UserOrderDto {
    private String addressStart;
    private String addressEnd;
    private Integer userId;
    private Integer type;
    private String notes;
}
