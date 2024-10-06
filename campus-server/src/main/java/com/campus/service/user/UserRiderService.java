package com.campus.service.user;

import com.dto.OrderPageDto;
import com.dto.RiderAcceptOrderDto;
import com.dto.RiderRefundOrderDto;
import com.result.PageResult;

public interface UserRiderService {
    void acceptOrder(RiderAcceptOrderDto riderAcceptOrderDto);

    Double showRiderWallet(Integer id);

    void WithDrawals(Integer id);

    PageResult showOrder(OrderPageDto orderPageDto);


    PageResult showRiderOrder(OrderPageDto orderPageDto);

    void riderRefundOrder(RiderRefundOrderDto riderRefundOrderDto);

    void confirm(Integer orderId);
}
