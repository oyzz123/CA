package com.exception;

/**
 * 超市订单不存在异常
 */
public class ShopOrderNotFound extends BaseException{
    public ShopOrderNotFound(String message) {
        super(message);
    }
}
