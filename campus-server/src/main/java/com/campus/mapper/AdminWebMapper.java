package com.campus.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminWebMapper {

    /**
     * 0无人接单 1骑手接单 2骑手送达 3用户签收 4用户申请售后 5申诉成功 6骑手投诉
     *
     * 查询超市月流水
     * @param year
     * @param month
     * @return
     */
    @Select("select sum(money) from shopping_order where status = 3 and month(create_time)=#{month} and year(create_time)=#{year}")
    Double monthShopQuery(int year,int month);

    /**
     * 查询跑腿月流水
     * @param year
     * @param month
     * @return
     */
    @Select("select sum(money) from takeaway_order where status = 3 and month(create_time)=#{month} and year(create_time)=#{year}")
    Double  monthTakeawayQuery(int year,int month);

    /**
     * 查询超市年流水
     * @param year
     * @return
     */
    @Select("select sum(money) from shopping_order where status = 3 and year(create_time)=#{year}")
    Double  yearShopQuery(int year);

    /**
     * 查询跑腿年流水
     * @param year
     * @return
     */
    @Select("select sum(money) from takeaway_order where status = 3 and year(create_time)=#{year}")
    Double  yearTakeawayQuery(int year);

    /**
     * 查询月使用超市用户人数
     * @param year
     * @param month
     * @return
     */
    @Select("select  count(distinct user_id) from shopping_order where status = 3 and month(create_time)=#{month} and year(create_time)=#{year}")
    Integer monthShopUser(int year,int month);

    /**
     * 查询年使用超市用户人数
     * @param year
     * @return
     */
    @Select("select count(distinct user_id) from shopping_order where status = 3 and year(create_time)=#{year}")
    Integer yearShopUser(int year);

    /**
     * 查询月使用跑腿用户人数
     * @param year
     * @param month
     * @return
     */
    @Select("select  count( distinct user_id) from takeaway_order where status = 3 and month(create_time)=#{month} and year(create_time)=#{year}")
    Integer monthTakeawayUser(int year,int month);

    /**
     * 查询年使用跑腿用户人数
     * @param year
     * @return
     */
    @Select("select  count(distinct user_id) from takeaway_order where status = 3  and year(create_time)=#{year}")
    Integer yearTakeawayUser(int year);

    /**
     * 查询超市月完成订单数
     * @param year
     * @param month
     * @return
     */
    @Select("select count(id) from shopping_order where status = 3 and month(create_time)=#{month} and year(create_time)=#{year}")
    Integer monthShopOrder(int year,int month);

    /**
     * 查询超市年完成订单数
     * @param year
     * @return
     */
    @Select("select  count(id) from shopping_order where status = 3  and year(create_time)=#{year}")
    Integer yearShopOrder(int year);

    /**
     * 查询跑腿月完成订单数
     * @param year
     * @param month
     * @return
     */
    @Select("select  count(id) from takeaway_order where status = 3  and month(create_time)=#{month} and year(create_time)=#{year}")
    Integer monthTakeawayOrder(int year,int month);

    /**
     * 查询跑腿年完成订单数
     * @param year
     * @return
     */
    @Select("select count(id) from takeaway_order where status = 3  and year(create_time)=#{year}")
    Integer yearTakeawayOrder(int year);
}
