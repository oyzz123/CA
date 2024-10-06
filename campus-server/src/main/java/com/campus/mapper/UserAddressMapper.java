package com.campus.mapper;

import com.entity.UserAddress;
import com.vo.UserAddressVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

@Mapper
public interface UserAddressMapper {
    /**
     * 新增地址
     * @param userAddressVo
     */
    @Insert("INSERT INTO user_address (user_name, user_id, user_telephone, full_location, province_code, city_code, county_code, address, status, create_user, create_time) " +
            "values (#{userName},#{userId},#{userTelephone},#{fullLocation},#{provinceCode},#{cityCode},#{countyCode},#{address},#{status},#{createUser},#{createTime})")
    void addAddress(UserAddressVo userAddressVo);

    /**
     * 修改地址
     * @param userAddressVo
     */
    void modifyAddress(UserAddressVo userAddressVo);

    /**
     * 查看用户现存所有地址
     * 0 删除 1可以用 2 默认地址
     * @param userId
     * @return
     */
    @Select("select * from user_address where user_id = #{userId} and status in(1,2)")
    ArrayList<UserAddress> getAllAddress(Integer userId);

    /**
     * 设置默认地址
     * @param id
     * @param userId
     */
    @Update("update user_address set status = 2 where id = #{id} and user_id = #{userId}")
    void setDefaultAddress(Integer id, Integer userId);

    /**
     * 获取默认地址
     * @param userId
     * @return
     */
    @Select("select * from user_address where user_id = user_id and status = 2")
    UserAddress getDefaultAddress(Integer userId);

    /**
     * 删除地址
     * @param id
     */
    @Update("update user_address set status = 0 where id = #{id}")
    void delete(Integer id);

    /**
     * 修改默认地址
     * @param id
     */
    @Update("update user_address set status = 1 where id = #{id}")
    void modifyDefaultAddress(Integer id);

    /**
     * 根据id获得地址
     * @param id
     * @return
     */
    @Select("select * from user_address where id = #{id}")
    UserAddress getAddress(Integer id);
}
