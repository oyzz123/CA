package com.campus.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginVo {
    private Integer id;
    private String account;
    private String name;
    private String token;
}
