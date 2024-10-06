package com.campus.service.admin.impl;

import com.campus.mapper.AdminMapper;
import com.campus.service.admin.AdminService;
import com.dto.AdminUpdateDto;
import com.dto.LoginDto;
import com.entity.Admin;
import com.exception.AccountNotFoundException;
import com.exception.PasswordErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 管理员登录功能
     * @param loginDto
     * @return
     */
    @Override
    public Admin login(LoginDto loginDto) {
        Admin admin = adminMapper.getByAccount(loginDto.getAccount());
        if(admin == null){
            throw new AccountNotFoundException("账号不存在");
        }
        String password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        System.out.println(password);
        if(!password.equals(admin.getPassword())){
            throw new PasswordErrorException("密码错误");
        }
        return admin;
    }

    /**
     * 管理员修改个人信息
     * @param adminUpdateDto
     */
    @Override
    public void update(AdminUpdateDto adminUpdateDto) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminUpdateDto,admin);
        adminMapper.update(admin);
    }

    /**
     * 管理员查询个人信息
     * @return
     */
    @Override
    public Admin query(int id) {
        Admin admin =adminMapper.query(id);
        return admin;
    }

}
