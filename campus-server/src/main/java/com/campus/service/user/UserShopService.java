package com.campus.service.user;

import com.dto.*;
import com.entity.ShopOrder;
import com.result.PageResult;
import com.vo.BossVo;
import com.vo.CommodityVo;

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
