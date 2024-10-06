package com.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TakeawayOrderVo {
    private Integer id;
    private String addressStart;
    private String addressEnd;
    private double money;
    private int status;
    private int type;
    private String notes;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String userNickname;
    private String userTelephone;
    private String riderNickname;
    private String riderTelephone;
}
