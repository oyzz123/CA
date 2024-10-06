package com.campus.service.user;

import com.campus.dto.*;
import com.dto.*;
import com.campus.entity.ShopOrder;
import com.campus.result.PageResult;
import com.campus.vo.BossVo;
import com.campus.vo.CommodityVo;

import java.util.ArrayList;
import java.util.List;

public interface UserShopService {
    Integer payCommodity(ShopOrderDto shopOrderDto);

    BossVo showBoss();

    PageResult showCommodity(CommodityPageDto commodityPageDto);

    PageResult showShopOrder(OrderPageDto OrderPageDto);

    CommodityVo getCommodityById(Integer id);

    void submitOrder(List<OrderCommodityVo> list);

    List<CommodityVo> recommend(RecommendDto recommendDto);

    ArrayList<CommodityVo> recommendSome();

    void confirmShopOrder(Integer id);

    ShopOrder findShopOrder(Integer orderId);
}
