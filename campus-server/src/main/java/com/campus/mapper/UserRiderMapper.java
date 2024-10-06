package com.campus.mapper;

import com.campus.annotation.AutoFill;
import com.dto.OrderPageDto;
import com.dto.RiderAcceptOrderDto;
import com.dto.RiderRefundOrderDto;
import com.entity.RiderRefundOrder;
import com.enumeration.OperationType;
import com.github.pagehelper.Page;
import com.vo.OrderVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserRiderMapper {
    /**
     * 接单
     * @param riderAcceptOrderDto
     */
//    @AutoFill(value = OperationType.INSERT)
    @Update("update takeaway_order set rider_id = #{riderId},status = 1 where id = #{orderId}")
    void acceptOrder(RiderAcceptOrderDto riderAcceptOrderDto);

    /**
     * 查看骑手钱包
     * @param id
     * @return
     */
    @Select("select rider_wallet from user where id = #{id}")
    Double selectRiderWallet(Integer id);

    /**
     * 展示所有订单
     * @param orderPageDto
     * @return
     */
    Page<OrderVo> selectAll(OrderPageDto orderPageDto);

    /**
     * 展示骑手订单
     * @param orderPageDto
     * @return
     */
    Page<OrderVo> showRiderOrder(OrderPageDto orderPageDto);

    /**
     * 骑手投诉订单
     * @param riderRefundOrderDto
     */
    @Insert("insert into complaints_rider(rider_id, order_id, message)" +
            "value" +
            "(#{riderId},#{orderId},#{message})")
    void submitRefund(RiderRefundOrderDto riderRefundOrderDto);

    @Select("select * from complaints_rider where rider_id = #{riderId}")
    RiderRefundOrder selectRefundById(Integer riderId);

    @Update("update takeaway_order set status = 2 where id = #{orderId}")
    void confirm(Integer orderId);

    @Select("select status from takeaway_order where id = #{orderId}")
    Integer riderConfirm(Integer orderId);
}
