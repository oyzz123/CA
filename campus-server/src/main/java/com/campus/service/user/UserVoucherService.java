package com.campus.service.user;

public interface UserVoucherService {
    void getVoucher(Integer userId, Integer id);

    void killVoucher(Integer userId, Integer id) throws InterruptedException;
}
