package com.campus.entity;

import lombok.Data;

@Data
public class RecommendOld {
    Integer typeId;
    Integer num;


    public RecommendOld(Integer typeId, Integer num) {
        this.typeId = typeId;
        this.num = num;
    }
}
