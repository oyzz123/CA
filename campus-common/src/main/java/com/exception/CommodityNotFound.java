package com.exception;

/**
 * 商品不存在异常
 */
public class CommodityNotFound extends BaseException{
    public CommodityNotFound(String message) {
        super(message);
    }
}
