package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import net.sf.jsqlparser.schema.Column;

import java.time.LocalDateTime;

@Data
@ApiModel
public class Commodity {

    private Integer id;

    private Integer commodityClassId;

    private String commodityName;

    private String price;

    private Integer allowance;

    private String weight;

    private String photoPath;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Long createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Long updateUser;
}
