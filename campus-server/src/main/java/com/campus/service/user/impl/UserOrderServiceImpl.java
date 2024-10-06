package com.campus.service.user.impl;

import com.campus.mapper.UserMapper;
import com.campus.service.user.UserOrderService;
import com.campus.mapper.UserOrderMapper;
import com.campus.dto.OrderPageDto;
import com.campus.dto.RefundOrderDto;
import com.campus.dto.UserOrderDto;
import com.campus.entity.User;
import com.campus.entity.UserOrder;
import com.campus.exception.OrderNotFound;
import com.campus.exception.RefundOrderExist;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.campus.result.PageResult;
import com.campus.vo.OrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户找跑腿业务处理
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户提交跑腿订单
     *
     * @param userOrderDto
     * @return
     */
    @Override
    @Transactional
    public Integer submit(UserOrderDto userOrderDto) {
        UserOrder userOrder = new UserOrder();
        BeanUtils.copyProperties(userOrderDto,userOrder);
        userOrder.setStatus(0);
        if(userOrderDto.getType() == 1){
            userOrder.setMoney(3);
        }else if(userOrderDto.getType() == 2){
            userOrder.setMoney(5);
        }
        User user = new User();
        user.setId(userOrder.getUserId());
        user.setWallet(1 * userOrder.getMoney());
        userMapper.update(user);
        userOrderMapper.submit(userOrder);
        return userOrder.getId();
    }

    /**
     * 根据订单id查询订单状态
     * @param id
     * @return
     */
    @Override
    public int showOrderStatus(Integer id) {
        if(userOrderMapper.getOrderById(id) == null) {
            throw new OrderNotFound("订单不存在");
        }else{
            return userOrderMapper.showOrderStatus(id);
        }
    }

    /**
     * 用户确认收货
     * @param id
     */
    @Override
    public void ConfirmOrder(Integer id) {
        if(userOrderMapper.getOrderById(id) == null) {
            throw new OrderNotFound("订单不存在");
        }else{
            UserOrder userOrder = new UserOrder();
            userOrder.setId(id);
            userOrder.setStatus(3);
            userOrderMapper.update(userOrder);
            Double money = userOrderMapper.getMoneyByOrderId(id);
            Integer userId = userOrderMapper.getUserIdByOrderId(id);
            Integer riderId = userOrderMapper.getRiderIdByOrderId(id);
            User user = new User();
            user.setId(userId);
            user.setWallet(-money);
            System.out.println(user.toString());
            userMapper.update(user);

            User user2 = new User();
            user2.setId(riderId);
            user2.setRiderWallet(money);
            System.out.println(user2.toString());
            userMapper.update(user2);
        }
    }

    /**
     * 申请退款
     * @param refundOrderDto
     */
    @Override
    public void refundOrder(RefundOrderDto refundOrderDto) {
        UserOrder userOrder = new UserOrder();
        userOrder.setStatus(4);
        userOrder.setId(refundOrderDto.getOrderId());
        if(userOrderMapper.findMessage(refundOrderDto.getOrderId()) != null){
            throw new RefundOrderExist("已有未处理的退款申请");
        }else{
            //没有退款记录
            //修改订单状态
            userOrderMapper.update(userOrder);
            //保存投诉信息
            userOrderMapper.saveComplaint(refundOrderDto);
        }
    }


    /**
     * 查看个人跑腿订单
     * @param orderPageDto
     * @return
     */
    @Override
    public PageResult showOrder(OrderPageDto orderPageDto) {
        PageHelper.startPage(orderPageDto.getPage(),orderPageDto.getPageSize());
        Page<OrderVo> page = userOrderMapper.showOrder(orderPageDto);
        Long total = page.getTotal();
        List<OrderVo> records = page.getResult();
        return new PageResult(total,records);
    }
}
