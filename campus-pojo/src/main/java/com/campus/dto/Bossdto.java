package com.campus.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
@ApiModel
public class Bossdto {

    private Integer id;

    private String nickname;

    private String account;

    private String password;

    private String sex;

    private Integer age;

    private String telephone;

    private Double wallet;
}
