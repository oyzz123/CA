package com.campus.mapper;

import com.campus.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where account = #{username}")
    User getByUserName(String username);

    /**
     * 更新用户信息
     * @param user
     */
//    @AutoFill(value = OperationType.UPDATE)
    void update(User user);

    /**
     * 用户注销账号
     * @param id
     */
    @Update("update user set status = 2 where id =#{id}")
//    @AutoFill(value = OperationType.UPDATE)
    void deregister(Integer id);


    /**
     * 支付
     * @param user
     */
//    @AutoFill(OperationType.INSERT)
    void pay(User user);

    /**
     * 支付优惠券
     * @param user
     */
    @Update("update user set wallet = #{wallet} where id = #{id}")
    void payVoucher(User user);


    /**
     * 查看钱包
     * @param id
     * @return
     */
    @Select("select wallet from user where id = #{id}")
    Double selectWallet(Integer id);

    /**
     * 根据用户id查找用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User selectUSerById(Integer id);

    /**
     * 注册
     * @param user
     */
//    @AutoFill(OperationType.INSERT)
    @Insert("insert into user (nickname, account, password, gender, age, telephone, status, wallet, rider_wallet,create_user,create_time,update_user,update_time)" +
            "values (#{nickname},#{account},#{password},#{gender},#{age},#{telephone},#{status},#{wallet},#{riderWallet},#{createUser},#{createTime},#{updateUser},#{updateTime})")
    void submit(User user);

    @Select("select * from user where account = #{account}")
    User selectUserByAccount(String account);
}
