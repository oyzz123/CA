package com.campus.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String nickname;
    private String account;
    private String password;
    private String gender;
    private int age;
    private String telephone;
}