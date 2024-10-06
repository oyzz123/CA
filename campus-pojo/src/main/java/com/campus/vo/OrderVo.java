package com.campus.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderVo {
    int id;
    private String addressStart;
    private String addressEnd;
    private int riderId;
    private int userId;
    private double money;
    private int status;
    private int type;
    private String notes;
    private String riderNickname;
    private String riderTelephone;
    private String telephone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
