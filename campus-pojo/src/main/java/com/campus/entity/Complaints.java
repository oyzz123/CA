package com.campus.entity;

import lombok.Data;

@Data
public class Complaints {
    Integer id;
    Integer userId;
    Integer riderId;
    String message;

}
