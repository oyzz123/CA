package com.campus.mapper;

import com.campus.annotation.AutoFill;
import com.dto.OrderPageDto;
import com.dto.RefundOrderDto;
import com.dto.UserOrderDto;
import com.entity.Complaints;
import com.entity.UserOrder;
import com.enumeration.OperationType;
import com.github.pagehelper.Page;
import com.vo.OrderVo;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserOrderMapper {
    /**
     * 提交订单
     * @param userOrder
     */
//    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into takeaway_order " +
            "(address_start, address_end, rider_id, user_id, money, status, type, notes, create_user, create_time, update_user, update_time) " +
            "VALUES " +
            "(#{addressStart},#{addressEnd},#{riderId},#{userId},#{money},#{status},#{type},#{notes},#{createUser},#{createTime},#{updateUser},#{updateTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void submit(UserOrder userOrder);

    /**
     * 查看订单状态
     * @param id
     * @return
     */
    @Select("select status from takeaway_order where id = #{id}")
    Integer showOrderStatus(Integer id);

    /**
     * 更新订单
     * @param userOrder
     */
//    @AutoFill(value = OperationType.UPDATE)
    void update(UserOrder userOrder);

    /**
     * 申请退款
     * @param refundOrderDto
     */
    @Insert("insert into complaints(user_id, orders_id, message) " +
            "VALUE " +
            "(#{userId},#{orderId},#{message})")
    void saveComplaint(RefundOrderDto refundOrderDto);

    /**
     * 展示用户跑腿订单
     * @param orderPageDto
     * @return
     */
    Page<OrderVo> showOrder(OrderPageDto orderPageDto);

    /**
     * 查看订单是否有退款记录
     * @param orderId
     * @return
     */
    @Select("select * from complaints where orders_id = #{orderId}")
    Complaints findMessage(Integer orderId);

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    @Select("select * from takeaway_order where id = #{id}")
    UserOrder getOrderById(Integer id);

    @Select("select money from takeaway_order where id = #{id}")
    Double getMoneyByOrderId(Integer id);


    @Select("select user_id from takeaway_order where id = #{id}")
    Integer getUserIdByOrderId(Integer id);

    @Select("select rider_id from takeaway_order where id = #{id}")
    Integer getRiderIdByOrderId(Integer id);
}
