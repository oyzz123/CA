package com.campus.service.admin;

import com.dto.AdminUserPageDto;
import com.dto.AdminUserSaveDto;
import com.dto.AdminUserUpdateDto;
import com.entity.User;
import com.result.PageResult;

public interface AdminUserService {
    /**
     * 管理员分页查询所有用户信息
     * @return
     */
    public PageResult pageQuery(AdminUserPageDto adminUserPageDto);


    /**
     * 管理员根据id查询用户信息
     * @param id
     * @return
     */
    User getById(int id);

    /**
     * 管理员修改用户信息
     * @param userDto
     */
    void update(AdminUserUpdateDto userDto);

    /**
     * 管理员删除用户信息
     * @param id
     */
    void deleteById(int id);

    /**
     * 管理员删除已注销用户信息
     */
    void deregister();

    /**
     * 管理员添加用户信息
     */
    void save(AdminUserSaveDto adminUserSaveDto);


}
