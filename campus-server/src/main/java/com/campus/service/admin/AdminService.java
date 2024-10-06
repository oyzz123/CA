package com.campus.service.admin;

import com.dto.AdminLoginDto;
import com.dto.AdminUpdateDto;
import com.dto.LoginDto;
import com.entity.Admin;

import javax.security.auth.login.AccountNotFoundException;

public interface AdminService {
    /**
     * 管理员修改个人信息表
     * @param adminUpdateDto
     */
    void update(AdminUpdateDto adminUpdateDto);

    /**
     * 管理员查询个人信息
     * @return
     */
    Admin query(int id);

    /**
     * 管理员登录
     * @param loginDto
     * @return
     */
    Admin login(LoginDto loginDto);
}
