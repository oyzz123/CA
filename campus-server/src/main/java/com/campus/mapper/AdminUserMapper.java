package com.campus.mapper;


import com.campus.dto.AdminUserPageDto;
import com.campus.entity.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface AdminUserMapper {

    /**
     * 管理员分页查询所有用户信息
     *
     * @return
     */
    Page<User> pageQuery(AdminUserPageDto adminUserPageDto);

    /**
     * 管理员根据昵称模糊查询用户信息
     * @param nickname
     * @return
     */
    @Select("select * from user where nickname like concat('%',#{nickname},'%')")
    ArrayList<User> getByName(String nickname);

    /**
     * 管理员根据id查询用户信息
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    User getById(int id);

    /**
     * 管理员修改用户信息
     * @param user
     */
//    @AutoFill(value = OperationType.UPDATE)
    void update(User user);

    /**
     * status 0禁用1启用2用户自己注销账号
     * 管理员删除用户信息
     * @param id
     */
//    @AutoFill(value = OperationType.UPDATE)
    @Update("update user set status = 0 where id = #{id}")
    void deleteById(int id);

    /**
     * 管理员删除已注销用户信息
     */
    @Delete("delete from user where status = 2")
    void deregister();


    /**
     * 管理员添加用户
     * @param user
     */
//    @AutoFill(OperationType.INSERT)
    void save(User user);



}
