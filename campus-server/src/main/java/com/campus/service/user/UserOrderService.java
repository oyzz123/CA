package com.campus.service.user;

import com.campus.dto.OrderPageDto;
import com.campus.dto.RefundOrderDto;
import com.campus.dto.UserOrderDto;
import com.campus.result.PageResult;

public interface UserOrderService {
    Integer submit(UserOrderDto userOrderDto);

    int showOrderStatus(Integer id);

    void ConfirmOrder(Integer id);

    void refundOrder(RefundOrderDto refundOrderDto);

    PageResult showOrder(OrderPageDto orderPageDto);
}
