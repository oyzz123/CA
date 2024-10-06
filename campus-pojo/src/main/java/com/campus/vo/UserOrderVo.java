package com.campus.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserOrderVo {
    private Integer id;

    private String addressStart;

    private String addressEnd;

    private String userNickname;

    private String userTelephone;

    private String riderNickname;

    private String riderTelephone;

    private double money;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private int status;

    private int type;

    private String notes;

    private String message;
}
