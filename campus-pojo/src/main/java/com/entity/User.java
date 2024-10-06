package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;

    private String nickname;

    private String account;

    private String password;

    private String gender;

    private int age;

    private String telephone;

    private int status;

    private double wallet;

    private double riderWallet;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Integer updateUser;
}