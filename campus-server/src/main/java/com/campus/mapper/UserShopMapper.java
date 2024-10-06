package com.campus.mapper;

import com.campus.dto.CommodityPageDto;
import com.campus.dto.OrderCommodityVo;
import com.campus.dto.OrderPageDto;
import com.campus.entity.*;
import com.entity.*;
import com.github.pagehelper.Page;
import com.campus.vo.BossVo;
import com.campus.vo.CommodityVo;
import com.campus.vo.UserShopOrderVo;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.HashSet;

@Mapper
public interface UserShopMapper {

    /**
     * 新增商品订单信息
     * @param shopOrder
     */
    @Insert("insert into shopping_order(user_id, status, money, address_end, commodities, notes, create_user, create_time, update_user, update_time)" +
            "value " +
            "(#{userId},#{status},#{money},#{addressEnd},#{commodities},#{notes},#{createUser},#{createTime},#{updateUser},#{updateTime})")
//    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void save(ShopOrder shopOrder);

    /**
     * 查看老板信息
     * @return
     */
    @Select("select id,nickname,account,gender,age,telephone from boss")
    BossVo showBoss();

    /**
     * 展示商品信息
     * @param commodityPageDto
     * @return
     */
    Page<Commodity> showCommodity(CommodityPageDto commodityPageDto);

    /**
     * 展示用户订单信息
     * @param OrderPageDto
     * @return
     */
    Page<UserShopOrderVo> showShopOrder(OrderPageDto OrderPageDto);

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @Select("select id,commodity_name,price,weight,photo_path,status,commodity_class_id from commodity where id = #{id}")
    CommodityVo getById(Integer id);

    /**
     * 新增订单商品表
     * @param orderCommodityVo
     */
    @Insert("insert into order_commodity (commodity_id, num, type_id, user_id) VALUES " +
            "(#{commodityId},#{num},#{typeId},#{userId})")
//    @AutoFill(OperationType.INSERT)
    void submit(OrderCommodityVo orderCommodityVo);

    /**
     * 修改订单商品表
     * @param orderCommodityVo
     */
    @Update("update order_commodity set num = num + #{num} where commodity_id = #{commodityId}")
//    @AutoFill(OperationType.UPDATE)
    void updateOrderCommodity(OrderCommodityVo orderCommodityVo);

    /**
     * 计算用户订单数量
     * @param userId
     * @return
     */
    @Select("select count(id) from order_commodity where user_id = #{userId}")
    Integer findUserOrder(Integer userId);

    /**
     * 根据商品id查询商品是否已存在于订单商品表中
     * @param commodityId
     * @return
     */
    @Select("select * from order_commodity where commodity_id = #{commodityId}")
    OrderCommodity findOrderCommodities(int commodityId);


    /**
     * 查询订单商品表中商品数量
     * 新用户
     *
     * @return
     */
    @Select("select commodity_id,num from order_commodity")
    ArrayList<RecommendNew> selectCommodityNum();


    /**
     * 根据用户查询订单商品表中商品类型
     * 老用户
     * @return
     */
    @Select("select type_id from order_commodity where user_id = #{userId}")
    HashSet<Integer> selectCommodityTypeByUserId(Integer userId);


    /**
     * 根据商品类型以及用户id查找数量
     * @param typeId
     * @return
     */
    @Select("select count(*) from order_commodity where type_id = #{typeId} and user_id = #{userId}")
    Integer selectCommodityNumByIdType(Integer typeId,Integer userId);

    /**
     * 根据商品分类id查找商品以及商品销量
     * @param typeId
     * @return
     */
    @Select("select commodity_id,num from order_commodity where type_id = #{typeId}")
    ArrayList<RecommendNew> selectCommodityNumByType(Integer typeId);

    /**
     * 用户确认收货
     * @param shopOrder
     */
    @Update("update shopping_order set status = #{status} where id = #{id}")
//    @AutoFill(OperationType.UPDATE)
    void updateOrder(ShopOrder shopOrder);

    /**
     * 根据id查询超市订单
     * @param id
     * @return
     */
    @Select("select * from shopping_order where id = #{id}")
    ShopOrder selectOrderById(Integer id);

    /**
     * 更新老板钱包
     * @param boss
     */
    @Update("update boss set wallet = #{wallet} where id = #{id}")
    void updateBossWallet(Boss boss);
}
