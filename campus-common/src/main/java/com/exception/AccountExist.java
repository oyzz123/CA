package com.exception;

/**
 * 注册时账号已被占用
 */
public class AccountExist extends BaseException{
    public AccountExist(String message){
        super(message);
    }
}
