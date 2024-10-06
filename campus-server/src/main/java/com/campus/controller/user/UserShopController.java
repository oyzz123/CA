package com.campus.controller.user;

import com.campus.service.boss.BossService;
import com.campus.service.user.UserShopService;
import com.dto.*;
import com.entity.Commodity;
import com.entity.ShopOrder;
import com.result.PageResult;
import com.result.Result;
import com.vo.BossVo;
import com.vo.CommodityVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/shop")
@Api(tags = "用户购物相关接口")
@Slf4j
@CrossOrigin
public class UserShopController {
   @Autowired
    private UserShopService userShopService;
   @Autowired
   private BossService bossService;

    /**
     * 商品推荐
     * @param recommendDto
     * @return
     */
    @ApiOperation("商品推荐")
    @GetMapping("/recommend")
   public Result<List> Recommend(RecommendDto recommendDto){
        List<CommodityVo> list = userShopService.recommend(recommendDto);
        return Result.success(list);
   }


    /**
     * 推荐三个热门商品
     * @return
     */
   @ApiOperation("推荐三个热门商品")
    @GetMapping("/rs")
    public Result<List> RecommendSome(){
        ArrayList<CommodityVo> list = userShopService.recommendSome();
        return Result.success(list);
    }

   /**
     * 展示商品详细信息
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("展示商品信息")
    public Result<PageResult> showCommodity(CommodityPageDto commodityPageDto){
        log.info("商品信息查询开始...");
        PageResult pageResult = userShopService.showCommodity(commodityPageDto);
        return Result.success(pageResult);
    }

    /**
     * 查看老板信息
     * @return
     */
    @ApiOperation("查看老板信息")
    @GetMapping("/boss")
    public Result<BossVo> showBoss(){
        return Result.success(userShopService.showBoss());
    }

    /**
     * 超市商品支付
     * @param shopOrderDto
     * @return
     */
    @PostMapping("/pay")
    @ApiOperation("支付商品订单")
    public Result PayCommodity(@RequestBody ShopOrderDto shopOrderDto){
        Integer id = userShopService.payCommodity(shopOrderDto);
        return Result.success(id);
    }

    /**
     * 提交用户订单信息
     * @param list
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("提交订单")
    public Result submitOrder(@RequestBody List<OrderCommodityVo> list){
        userShopService.submitOrder(list);
        bossService.processOrder(list);
        return Result.success();
    }

    /**
     * 展示用户购物订单
     * @return
     */
    @GetMapping("/order")
    @ApiOperation("展示用户购物订单")
    public Result<PageResult> showShopOrder(OrderPageDto OrderPageDto){
        PageResult pageResult = userShopService.showShopOrder(OrderPageDto);
        return Result.success(pageResult);
    }

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @ApiOperation("根据id查询商品信息")
    @GetMapping("/{id}")
    public Result<CommodityVo> getCommodityById(@PathVariable Integer id){
        return Result.success(userShopService.getCommodityById(id));
    }


    /**
     * 用户确认收货
     * @param id
     * @return
     */
    @ApiOperation("用户确认收货")
    @PutMapping("/shopOrder/{id}")
    public Result confirmShopOrder(@PathVariable Integer id) {
        userShopService.confirmShopOrder(id);
        return Result.success();
    }

    /**
     * 根据订单id查找订单详细信息
     * @param orderId
     * @return
     */
    @ApiOperation("根据订单id查找订单详细信息")
    @GetMapping("/findOrder/{orderId}")
    public Result findShopOrder(@PathVariable Integer orderId) {
        log.info("{}",orderId);
        ShopOrder shopOrder = userShopService.findShopOrder(orderId);
        return Result.success(shopOrder);
    }
}
