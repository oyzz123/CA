package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ShopOrder {
   private int id;
   private int userId;
   private int status;
   private double money;
   private String addressEnd;
   private String commodities;
   private String notes;
   private Integer createUser;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date createTime;
   private Integer updateUser;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date updateTime;
}
