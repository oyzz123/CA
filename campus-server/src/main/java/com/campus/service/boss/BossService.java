package com.campus.service.boss;

import com.campus.dto.*;
import com.dto.*;
import com.campus.entity.Boss;
import com.campus.entity.Commodity;
import com.campus.result.PageResult;
import com.campus.vo.ClassificationVo;

import java.util.List;

public interface BossService {

    Double findWallet(Integer id);

    void delete(Integer id);

    void Insert(CommodityDto commodityDto);

    void Update(CommodityDto commodityDto) throws InterruptedException;

    void Refund(Integer id);

    Commodity SelectById(Integer id);

    PageResult pageQuery(CommodityPageQueryDto commodityPageQueryDto);

    void processOrder(List<OrderCommodityVo> orderCommodityVo);

    double SelectMonth(Integer year, Integer month);

    double SelectYear(Integer year);

    PageResult UserPageQuery(UserPageQueryDto userPageQueryDto);

    PageResult riderPageQuery(UserPageQueryDto userPageQueryDto);

    void Refund2(Integer id);

    Boss login(LoginDto loginDto);

    void UpdateBoss(UpdateBossDto updateBossDto);

    String selectType(Integer id);

    void addType(ClassDto classDto);

    List<ClassificationVo> selectClass();

    List<ClassificationVo> getidname();

    PageResult shopOrderQuery(BossShoppingOrderVo bossShoppingOrderVo);

    PageResult takeawayOrderQuery(BossShoppingOrderVo bossShoppingOrderVo);

    void notRefund(Integer orderId);

    PageResult searchItem(CommoditySearchDto commoditySearchDto);

    void confirm(Integer id);
}