package com.campus.dto;
import lombok.Data;


@Data
public class UserAddressDto {
    private Integer id;
    private Integer userId;
    private String  userName;
    private String  userTelephone;
    private String  fullLocation;
    private String  provinceCode;
    private String  cityCode;
    private String countyCode;
    private String address;
}
