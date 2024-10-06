package com.campus.mapper;

import com.campus.annotation.AutoFill;
import com.entity.Admin;
import com.enumeration.OperationType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminMapper {

    /**
     * 管理员修改个人信息
     * @param admin
     */
//    @AutoFill(value = OperationType.UPDATE)
    void update(Admin admin);

     /**
      * 管理员登录
      * @param account
      * @param password
      * @return
      */
      @Select("select * from admin where account = #{account} and password = #{password}")
      Admin login(String account, String password);

      /**
       * 管理员查询个人信息
       * @return
       */
       @Select("select * from admin where id= #{id}")
       Admin query(int id);

       @Select("select * from admin where account = #{account}")
       Admin getByAccount(String account);
}
