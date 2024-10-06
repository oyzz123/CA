package com.campus.service.admin.impl;

import com.campus.mapper.AdminWebMapper;
import com.campus.service.admin.AdminWebService;
import com.constant.OrderTypeConstant;
import com.vo.AdminQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminWebServiceImpl implements AdminWebService {
    @Autowired
    private AdminWebMapper adminWebMapper;
    /**
     * 查询网站月流水
     * @param year
     * @param month
     * @return
     */
    @Override
    public AdminQueryVo monthSum(int year, int month) {
        AdminQueryVo adminQueryVo = new AdminQueryVo();
        adminQueryVo.setMonth(month);
        adminQueryVo.setType1(OrderTypeConstant.SHOP_ORDER);
        Double shopMoney = adminWebMapper.monthShopQuery(year, month);
        if(shopMoney==null){
            adminQueryVo.setShopMoney(0.0);
            shopMoney=0.0;
        }else{
            adminQueryVo.setShopMoney(shopMoney);
            adminQueryVo.setProfit(shopMoney*0.4);
        }
        Integer shopUsers = adminWebMapper.monthShopUser(year,month);
        if(shopUsers==null){
            adminQueryVo.setShopUsers(0);
            shopUsers=0;
        }else{
            adminQueryVo.setShopUsers(shopUsers);
        }
        Integer shopOrders =adminWebMapper.monthShopOrder(year,month);
        if(shopOrders==null){
            adminQueryVo.setShopOrders(0);
            shopOrders=0;
        }else{
            adminQueryVo.setShopOrders(shopOrders);
        }
        adminQueryVo.setType2(OrderTypeConstant.TAKEAWAY_ORDER);
        Integer takeawayUsers = adminWebMapper.monthTakeawayUser(year,month);
        if(takeawayUsers == null){
            adminQueryVo.setTakeawayUsers(0);
            takeawayUsers=0;
        }else{
            adminQueryVo.setTakeawayUsers(takeawayUsers);
        }
        Integer takeawayOrders = adminWebMapper.monthTakeawayOrder(year,month);
        if(takeawayOrders==null){
            adminQueryVo.setTakeawayOrders(0);
            takeawayOrders=0;
        }else{
            adminQueryVo.setTakeawayOrders(takeawayOrders);
        }
        Double takeawayMoney = adminWebMapper.monthTakeawayQuery(year, month);
        if(takeawayMoney==null){
            adminQueryVo.setTakeawayMoney(0.0);
            takeawayMoney=0.0;
        }else{
            adminQueryVo.setTakeawayMoney(takeawayMoney);
        }
        adminQueryVo.setSumUsers(shopUsers+takeawayUsers);
        adminQueryVo.setSumOrders(shopOrders+takeawayOrders);
        adminQueryVo.setSumMoney(shopMoney+takeawayMoney);
        return adminQueryVo;
    }

    /**
     * 查询网站年流水
     * @param year
     * @return
     */
    @Override
    public ArrayList<AdminQueryVo> yearSumQuery(int year) {
        ArrayList<AdminQueryVo> queryList = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            AdminQueryVo adminQueryVo = monthSum(year,i);
            queryList.add(adminQueryVo);
        }
        AdminQueryVo adminQueryVo = new AdminQueryVo();
        adminQueryVo.setMonth(13);
        adminQueryVo.setType1(OrderTypeConstant.SHOP_ORDER);
        adminQueryVo.setType2(OrderTypeConstant.TAKEAWAY_ORDER);
        Integer yearShopUsers = adminWebMapper.yearShopUser(year);
        if(yearShopUsers==null){
            adminQueryVo.setShopUsers(0);
            yearShopUsers=0;
        }else{
            adminQueryVo.setShopUsers(yearShopUsers);
        }
        Integer yearShopOrders = adminWebMapper.yearShopOrder(year);
        if(yearShopOrders==null){
            adminQueryVo.setShopOrders(0);
            yearShopOrders=0;
        }else{
            adminQueryVo.setShopOrders(yearShopOrders);
        }
        Double yearShopQuery = adminWebMapper.yearShopQuery(year);
        if(yearShopQuery==null){
            adminQueryVo.setShopMoney(0.0);
            yearShopQuery=0.0;
        }else {
            adminQueryVo.setShopMoney(yearShopQuery);
            adminQueryVo.setProfit(yearShopQuery*0.4);
        }
        Integer yearTakeawayUsers =  adminWebMapper.yearTakeawayUser(year);
        if(yearTakeawayUsers==null){
            adminQueryVo.setTakeawayUsers(0);
            yearTakeawayUsers=0;
        }else {
            adminQueryVo.setTakeawayUsers(yearTakeawayUsers);
        }
        Integer yearTakeawayOrders = adminWebMapper.yearTakeawayOrder(year);
        if(yearTakeawayOrders==null){
            adminQueryVo.setTakeawayOrders(0);
            yearTakeawayOrders=0;
        }else {
            adminQueryVo.setTakeawayOrders(yearTakeawayOrders);
        }
        Double yearTakeawayMoney = adminWebMapper.yearTakeawayQuery(year);
        if(yearTakeawayMoney==null){
            adminQueryVo.setTakeawayMoney(0.0);
            yearTakeawayMoney=0.0;
        }else{
            adminQueryVo.setTakeawayMoney(yearTakeawayMoney);
        }
        adminQueryVo.setSumUsers(yearShopUsers+yearTakeawayUsers);
        adminQueryVo.setSumOrders(yearShopOrders+yearTakeawayOrders);
        adminQueryVo.setSumMoney(yearTakeawayMoney+yearShopQuery);
        queryList.add(adminQueryVo);
        return queryList;
    }

}
