package com.campus.dto;

import lombok.Data;

@Data
public class OrderPageDto {
    private int page;
    private int pageSize;
    private Integer userId;
}
