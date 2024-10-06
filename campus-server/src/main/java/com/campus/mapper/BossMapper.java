package com.campus.mapper;

import com.dto.*;
import com.entity.*;
import com.github.pagehelper.Page;
import com.result.PageResult;
import com.vo.ClassificationVo;
import com.vo.ShopOrderVo;
import com.vo.TakeawayOrderVo;
import com.vo.UserOrderVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BossMapper {
    @Select("select wallet from boss where id = #{id}")
    Double findWallet(Integer id);

    @Update("update commodity set status = 0 where id = #{id}")
    void delete(Integer id);
    @Insert("insert into commodity(commodity_class_id,commodity_name,price,allowance,weight,photo_path,status) " +
            "values (#{commodityClassId},#{commodityName},#{price},#{allowance},#{weight},#{photoPath},#{status})")
    void InsertCommodity(CommodityTest commodityTest);

    void Update(CommodityDto commodityDto);

    @Select("select money from takeaway_order where id = #{id}")
    double Getprice(Integer id);
    @Update("update boss set wallet = wallet - #{money}")
    void Decrease(Double money);
    @Update("update user set wallet = wallet + #{money}")
    void Increase(Double money);
    @Select("select * from commodity where id = #{id}")
    Commodity getById(Integer id);

    Page<Commodity> pageQuery(CommodityPageQueryDto commodityPageQueryDto);

    @Select("SELECT SUM(money) FROM shopping_order WHERE create_time >= #{startDate} AND create_time <= #{endDate}")
    Double SelectMonth(String startDate, String endDate);

    @Select("select sum(money) from shopping_order where create_time between #{startDate} and #{endDate}")
    List<Double> SelectYear(String startDate, String endDate);

    Page<UserOrderVo> UserPageQuery(UserPageQueryDto userPageQueryDto);

    Page<UserOrderVo> RiderPageQuery(UserPageQueryDto userPageQueryDto);

   @Update("update user set rider_wallet = rider_wallet + 1 where id = #{id}")
    void AddForRider(Integer id);

   @Update("update commodity set allowance = allowance - #{num} where id = #{id}")
    void deAllowance(Integer id, Integer num);

   @Select("select * from boss where account = #{account}")
    Boss getBossByAccount(String account);

//   @Update("update boss set nickname = #{nickname},password = #{password},gender = #{gender},telephone = #{telephone}")
    void UpdateBoss(Boss boss);

   @Select("select name from classification where id = #{id}")
    String selectType(Integer id);

   @Insert("insert into classification (name) values (#{name})")
    void addType(ClassDto classDto);

   @Select("select id,name from classification")
    List<ClassificationVo> ClassPageQuery();

    @Select("select id,name from classification")
    List<com.vo.ClassificationVo> getidname();

    Page<ShopOrderVo> shopOrderQuery(BossShoppingOrderVo bossShoppingOrderVo);

    Page<TakeawayOrderVo> takeawayOrderQuery(BossShoppingOrderVo bossShoppingOrderVo);

    @Update("update takeaway_order set status = 5 where id = #{id}")
    void updateUserOrderStatus(Integer id);

    @Update("update takeaway_order set status = 7 where id = #{id}")
    void notRefund(Integer id);
    @Select("select rider_id from takeaway_order where id = #{orderId}")
    Integer findUserIdByOrderId(Integer orderId);

    Page<Commodity> searchItem(String commodityName);

    @Update("update shopping_order set status = 2 where id = #{id}")
    void confirm(Integer id);
}
