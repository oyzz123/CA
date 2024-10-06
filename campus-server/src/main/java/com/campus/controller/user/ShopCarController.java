package com.campus.controller.user;

import com.campus.service.user.ShopCartService;
import com.dto.ShopCartDto;
import com.result.Result;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/user/shopcar")
@Api(tags = "购物车相关接口")
@Slf4j
@CrossOrigin
public class ShopCarController {
    @Autowired
    private ShopCartService shopCartService;

    /**
     * 新增购物车
     * @param shopCartDto
     * @return
     */
    @PostMapping("/1")
    public Result addCommodityForShopCar(@RequestBody ShopCartDto shopCartDto) {
        return shopCartService.addCommodity(shopCartDto);
    }


    /**
     * 查询购物车所有商品
     * @param userId
     * @return
     */
    @GetMapping("/1/{userId}")
    public Result allCommodity(@PathVariable Integer userId) {
        return shopCartService.selectAll(userId);
    }

    /**
     * 清除购物车
     * @param userId
     * @return
     */
    @DeleteMapping("/1/{userId}")
    public Result deleteAll(@PathVariable("userId") Integer userId) {
        return shopCartService.deleteAll(userId);
    }

    @DeleteMapping("/2/{userId}/{cId}")
    public Result deleteCommodityByCId(@PathVariable("userId") Integer userId,@PathVariable("cId") Integer cId) {
        return shopCartService.deleteCommodity(userId,cId);
    }
}
