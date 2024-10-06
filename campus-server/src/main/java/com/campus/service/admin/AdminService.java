package com.campus.service.admin;

import com.campus.dto.AdminUpdateDto;
import com.campus.dto.LoginDto;
import com.campus.entity.Admin;

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
