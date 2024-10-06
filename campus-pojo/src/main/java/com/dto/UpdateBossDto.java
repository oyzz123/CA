package com.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class UpdateBossDto {

    private String nickname;

    private String password;

    private String gender;

    private String telephone;
}
