package com.campus.service.user.impl;

import com.campus.mapper.UserMapper;
import com.campus.service.user.UserRiderService;
import com.campus.mapper.UserRiderMapper;
import com.dto.OrderPageDto;
import com.dto.RiderAcceptOrderDto;
import com.dto.RiderRefundOrderDto;
import com.entity.User;
import com.exception.ConfirmExist;
import com.exception.RefundOrderExist;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.result.PageResult;
import com.vo.OrderVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 骑手业务处理
 */
@Service
public class UserRiderServiceImpl implements UserRiderService {
    @Autowired
    private UserRiderMapper userRiderMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     * 骑手接单
     * @param riderAcceptOrderDto
     */
    @Override
    @ApiOperation("骑手接单")
    public void acceptOrder(RiderAcceptOrderDto riderAcceptOrderDto) {
        userRiderMapper.acceptOrder(riderAcceptOrderDto);
    }

    @ApiOperation("查看骑手钱包")
    @Override
    public Double showRiderWallet(Integer id) {
        return userRiderMapper.selectRiderWallet(id);
    }

    @Override
    @ApiOperation("提现骑手钱包")
    public void WithDrawals(Integer id) {
        //查看骑手钱包余额
        double money = userRiderMapper.selectRiderWallet(id);

        User user = new User();
        User user2 = new User();
        user.setId(id);
        user2.setId(id);
        user.setRiderWallet(-money);
        user2.setWallet(money);
        //骑手钱包归零
        userMapper.update(user);
        //更新用户钱包
        userMapper.update(user2);
    }

    /**
     * 查看所有可接单订单
     * @return
     */
    @Override
    public PageResult showOrder(OrderPageDto orderPageDto) {
        PageHelper.startPage(orderPageDto.getPage(),orderPageDto.getPageSize());
        Page<OrderVo> page = userRiderMapper.selectAll(orderPageDto);
        long total = page.getTotal();
        List<OrderVo> records = page.getResult();
        return new PageResult(total,records);
    }

    /**
     * 展示骑手所有接单记录
     * @param orderPageDto
     * @return
     */
    @Override
    public PageResult showRiderOrder(OrderPageDto orderPageDto) {
        PageHelper.startPage(orderPageDto.getPage(),orderPageDto.getPageSize());
        Page<OrderVo> page = userRiderMapper.showRiderOrder(orderPageDto);
        long total = page.getTotal();
        List<OrderVo> records = page.getResult();
        return new PageResult(total,records);
    }

    /**
     * 骑手投诉跑腿订单
     * @param riderRefundOrderDto
     */
    @Override
    public void riderRefundOrder(RiderRefundOrderDto riderRefundOrderDto) {
        if(userRiderMapper.selectRefundById(riderRefundOrderDto.getRiderId()) != null) {
            throw new RefundOrderExist("退款信息已存在");
        }else{
            userRiderMapper.submitRefund(riderRefundOrderDto);
        }
    }

//    0无人接单 1骑手接单 2骑手送达 3用户签收 4用户申请售后 5申诉成功 6骑手投诉
    @Override
    public void confirm(Integer orderId) {
        if(userRiderMapper.riderConfirm(orderId) == 2){
            throw  new ConfirmExist("已确认送达");
        }else{
            userRiderMapper.confirm(orderId);
            userRiderMapper.riderConfirm(orderId);
        }
    }
}
