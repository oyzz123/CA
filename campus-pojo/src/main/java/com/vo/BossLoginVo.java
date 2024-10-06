package com.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BossLoginVo {
    private Integer id;
    private String account;
    private String name;
    private String token;
}

