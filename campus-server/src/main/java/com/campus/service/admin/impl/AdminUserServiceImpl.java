package com.campus.service.admin.impl;

import com.campus.mapper.AdminUserMapper;
import com.campus.service.admin.AdminUserService;
import com.constant.PasswordConstant;
import com.constant.StatusConstant;
import com.dto.AdminUserPageDto;
import com.dto.AdminUserSaveDto;
import com.dto.AdminUserUpdateDto;
import com.entity.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.result.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;

    /**
     * 管理员分页查询所有用户信息
     * @return
     */
    @Override
    public PageResult pageQuery(AdminUserPageDto adminUserPageDto) {
        PageHelper.startPage(adminUserPageDto.getPage(),adminUserPageDto.getPageSize());
        Page<User> page = adminUserMapper.pageQuery(adminUserPageDto);
        long total = page.getTotal();
        List<User> result = page.getResult();
        return new PageResult(total,result);
    }

    /**
     * 管理员根据id查询用户信息
     * @param id
     * @return
     */
    @Override
    public User getById(int id) {
        User user= adminUserMapper.getById(id);
        return user;
    }

    /**
     * 管理员编辑用户信息
     * @param userDto
     */
    @Override
    public void update(AdminUserUpdateDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        adminUserMapper.update(user);
    }

    /**
     * 管理员删除用户信息
     * @param id
     */
    @Override
    public void deleteById(int id) {
        adminUserMapper.deleteById(id);
    }

    /**
     * 管理员删除已注销用户信息
     */
    @Override
    public void deregister() {
        adminUserMapper.deregister();
    }

    /**
     * 管理员添加用户信息
     */
    @Override
    public void save(AdminUserSaveDto adminUserSaveDto) {
        User user = new User();
        BeanUtils.copyProperties(adminUserSaveDto,user);
        //1正常 0禁用
        user.setStatus(StatusConstant.ENABLE);
        user.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.PASSWORD.getBytes()));

        adminUserMapper.save(user);
    }


}
