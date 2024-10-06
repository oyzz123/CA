package com.campus.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class Classification {
   private String name;

   private Integer status;

   private Integer createUser;

   private Integer updateUser;

   private Data createTime;

   private Data updateTime;
}
