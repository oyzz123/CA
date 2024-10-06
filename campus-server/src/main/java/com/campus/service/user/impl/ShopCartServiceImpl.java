package com.campus.service.user.impl;

import cn.hutool.json.JSONUtil;
import com.campus.mapper.UserShopMapper;
import com.campus.service.user.ShopCartService;
import com.constant.CommodityConstant;
import com.constant.ShopCartConstant;
import com.dto.ShopCartDto;
import com.entity.ShopCart;
import com.exception.CommodityNotFound;
import com.result.Result;
import com.vo.CommodityVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ShopCartServiceImpl implements ShopCartService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserShopMapper userShopMapper;

    /**
     * 更新购物车
     * 包括新增商品 更新商品数量
     * @param shopCartDto
     * @return
     */
    @Override
    public Result addCommodity(ShopCartDto shopCartDto) {
        //查询商品
        CommodityVo commodity = userShopMapper.getById(shopCartDto.getCommodityId());
        if(commodity == null) {
            throw new CommodityNotFound(CommodityConstant.COMMODITY_NOT_FOUND);
        }

        //查询redis中是否添加过
        boolean exist = Boolean.TRUE.equals(redisTemplate
                .opsForSet()
                .isMember(
                        ShopCartConstant.SHOP_CART_KEY + shopCartDto.getUserId() + ":total",
                        String.valueOf(commodity.getId())));
        ShopCart shopCart = new ShopCart();
        shopCart.setCommodity(commodity);
        shopCart.setNum(shopCartDto.getNum());
        shopCart.setUpdateTime(LocalDateTime.now());
        shopCart.setCheck(shopCartDto.getCheck());

        if(exist == false) {
            //未添加过
            //添加进购物车集合
            redisTemplate.opsForSet()
                    .add(ShopCartConstant.SHOP_CART_KEY + shopCartDto.getUserId() + ":total",
                            String.valueOf(commodity.getId()));
            //将商品信息添加进集合
            redisTemplate.opsForValue().set(
                    ShopCartConstant.SHOP_CART_KEY + shopCartDto.getUserId() + ":" + commodity.getId(),
                    JSONUtil.toJsonStr(shopCart)
            );
        }else{
            //添加过
            String str = redisTemplate.opsForValue()
                    .get(ShopCartConstant.SHOP_CART_KEY + shopCartDto.getUserId() + ":" + commodity.getId());
            ShopCart shopCart1 = JSONUtil.toBean(str,ShopCart.class);
            int num = shopCart1.getNum() + shopCartDto.getNum();
            if(num <= 0) {
                //相当于删除商品
                //删除商品详细信息
                redisTemplate.delete(ShopCartConstant.SHOP_CART_KEY + shopCartDto.getUserId() + ":" + commodity.getId());
                //删除购物车set中的商品id
                redisTemplate.opsForSet()
                        .remove(ShopCartConstant.SHOP_CART_KEY + shopCartDto.getUserId() + ":total",
                                String.valueOf(commodity.getId()));
            }else{
                //更新商品信息
                shopCart.setNum(num);
                redisTemplate.opsForValue()
                        .set(ShopCartConstant.SHOP_CART_KEY + shopCartDto.getUserId() + ":" + commodity.getId(),
                                JSONUtil.toJsonStr(shopCart));
            }
        }

        return Result.success();
    }

    /**
     * 查找购物车所有商品
     * @param userId
     * @return
     */
    @Override
    public Result selectAll(Integer userId) {
        Set<String> members = redisTemplate.opsForSet()
                .members(ShopCartConstant.SHOP_CART_KEY + userId + ":total");
        List<ShopCart> shopCarts = new ArrayList<>();
        for(String s : members) {
            String s1 = redisTemplate.opsForValue()
                    .get(ShopCartConstant.SHOP_CART_KEY + userId + ":" + Integer.valueOf(s));
            ShopCart shopCartTmp = JSONUtil.toBean(s1, ShopCart.class);
            shopCarts.add(shopCartTmp);
        }
        return Result.success(shopCarts);
    }


    /**
     * 清空购物车
     * @param id
     * @return
     */
    @Override
    public Result deleteAll(Integer id) {
        //查找所有商品id
        Set<String> members = redisTemplate.opsForSet()
                .members(ShopCartConstant.SHOP_CART_KEY + id + ":total");
        if(members == null) {
            System.out.println("1111111");
        }
        //删除用户购物车数据
        redisTemplate.delete(ShopCartConstant.SHOP_CART_KEY + id + ":total");
        //删除购物车中商品数据
        for(String s : members) {
            int c_id = Integer.valueOf(s);
            redisTemplate.delete(ShopCartConstant.SHOP_CART_KEY + id + ":" + c_id);
        }
        return Result.success();
    }


    /**
     * 根据用户id以及商品id清除某商品
     * @param userId
     * @param cId
     * @return
     */
    @Override
    public Result deleteCommodity(Integer userId, Integer cId) {
        //删除购物车列表中商品id
        redisTemplate.opsForSet()
                .remove(ShopCartConstant.SHOP_CART_KEY + userId + ":total",
                        String.valueOf(cId));

        //删除商品详细信息
        redisTemplate.delete(ShopCartConstant.SHOP_CART_KEY + userId + ":" + cId);
        return Result.success();
    }
}
