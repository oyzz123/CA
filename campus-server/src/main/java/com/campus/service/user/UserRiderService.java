package com.campus.service.user;

import com.campus.dto.OrderPageDto;
import com.campus.dto.RiderAcceptOrderDto;
import com.campus.dto.RiderRefundOrderDto;
import com.campus.result.PageResult;

public interface UserRiderService {
    void acceptOrder(RiderAcceptOrderDto riderAcceptOrderDto);

    Double showRiderWallet(Integer id);

    void WithDrawals(Integer id);

    PageResult showOrder(OrderPageDto orderPageDto);


    PageResult showRiderOrder(OrderPageDto orderPageDto);

    void riderRefundOrder(RiderRefundOrderDto riderRefundOrderDto);

    void confirm(Integer orderId);
}
