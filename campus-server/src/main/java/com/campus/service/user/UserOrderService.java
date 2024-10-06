package com.campus.service.user;

import com.dto.OrderPageDto;
import com.dto.RefundOrderDto;
import com.dto.UserOrderDto;
import com.result.PageResult;

public interface UserOrderService {
    Integer submit(UserOrderDto userOrderDto);

    int showOrderStatus(Integer id);

    void ConfirmOrder(Integer id);

    void refundOrder(RefundOrderDto refundOrderDto);

    PageResult showOrder(OrderPageDto orderPageDto);
}
