package com.exception;

/**
 * 已有退款申请异常
 */
public class RefundOrderExist extends BaseException{
    public RefundOrderExist(String message){
        super(message);
    }
}
