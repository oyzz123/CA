package com.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAddressVo {
    private Integer id;
    private Integer userId;
    private String  userName;
    private String  userTelephone;
    private String  fullLocation;
    private String  provinceCode;
    private String  cityCode;
    private String countyCode;
    private String address;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Integer updateUser;
}
