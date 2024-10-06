package com.campus.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class BossLoginDto {
    private String nickname;
    private String password;
}
