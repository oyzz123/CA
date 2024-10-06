package com.campus.exception;

/**
 * 用户跑腿订单不存在异常
 */
public class OrderNotFound extends BaseException {
    public OrderNotFound(String message) {
        super(message);
    }
}
