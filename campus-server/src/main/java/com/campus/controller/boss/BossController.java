package com.campus.controller.boss;

import com.campus.service.boss.BossService;
import com.dto.*;
import com.entity.Commodity;
import com.result.PageResult;
import com.result.Result;
import com.vo.ClassificationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/boss")
@RestController
@Api(tags = "老板相关接口")
@Slf4j
public class BossController {
    @Autowired
    private BossService bossService;
    //功能实现
    @GetMapping("/wallet/{id}")
    @ApiOperation(value = "查询钱包")
    public Result<Double> findWallet(@PathVariable("id") Integer id){
        Double money = bossService.findWallet(id);
        return Result.success(money);
    }
    //功能实现
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除商品")
    public Result delete(@PathVariable Integer id){
        bossService.delete(id);
        return Result.success();
    }
    //功能实现
    @PostMapping("/commodities")
    @ApiOperation(value = "增加商品")
    public Result Insert(@RequestBody CommodityDto commodityDto){
        bossService.Insert(commodityDto);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    @ApiOperation(value = "查询单个商品")
    public Result<Commodity > SelectById(@PathVariable Integer id){
        Commodity commodity = bossService.SelectById(id);
        return Result.success(commodity);
    }

    @GetMapping("/page")
    @ApiOperation(value = "商品分页查询")
    public Result<PageResult> page(CommodityPageQueryDto commodityPageQueryDto){
        PageResult pageResult = bossService.pageQuery(commodityPageQueryDto);
        return Result.success(pageResult);
    }
    @PutMapping
    @ApiOperation(value = "修改商品信息")
    public Result Update(@RequestBody CommodityDto commodityDto) throws InterruptedException {
        bossService.Update(commodityDto);
        return Result.success();
    }

    @GetMapping("/refund/{orderId}")
    @ApiOperation(value = "同意用户退款")
    public Result Refund(@PathVariable Integer orderId ){
        bossService.Refund(orderId);
        return Result.success();
    }
    @GetMapping("/refund2/{orderId}")
    @ApiOperation(value = "同意骑手退款")
    public Result Refund2(@PathVariable Integer orderId){
        log.info("订单id为 ：{}",orderId);
        System.out.println(orderId);
        bossService.Refund2(orderId);
        return Result.success();
    }

    @PutMapping("/notRefund/{orderId}")
    @ApiOperation(value = "不同意退款")
    public Result NotRefund(@PathVariable Integer orderId){
        bossService.notRefund(orderId);
        return Result.success();
    }

    //@GetMapping("/select-month/{year}")
    //@ApiOperation(value = "查询月流水")
    public Result<Double> SelectMonth(@PathVariable Integer year,Integer month)
    {   double money = bossService.SelectMonth(year,month);
        return Result.success(money);
    }


    //@GetMapping("/select-month/{year}{month}")
    //@ApiOperation(value = "查询年流水")
    public Result SelectYear(@PathVariable Integer year){
        double money = bossService.SelectYear(year);
        return Result.success(money);
    }


//    @PostMapping("/processOrder")
//    @ApiOperation(value = "处理订单")
    public Result processOrder(@RequestBody List<OrderCommodityVo> orderCommodityVo){
        bossService.processOrder(orderCommodityVo);
        return Result.success();
    }

    @GetMapping("/userComplaints")
    @ApiOperation(value = "用户投诉信息分页查询")
    public Result<PageResult> UserPageQuery(UserPageQueryDto userPageQueryDto){
        PageResult pageResult = bossService.UserPageQuery(userPageQueryDto);
        return Result.success(pageResult);
    }

    @GetMapping("/riserComplaints")
    @ApiOperation(value = "骑手投诉信息分页查询")
    public Result<PageResult> RiderPageQuery(UserPageQueryDto userPageQueryDto){
        PageResult pageResult = bossService.riderPageQuery(userPageQueryDto);
        return Result.success(pageResult);
    }


    @PutMapping("/updateBoss")
    @ApiOperation(value = "修改老板信息")
    public Result UpdateBoss(@RequestBody UpdateBossDto updateBossDto){
        bossService.UpdateBoss(updateBossDto);
        return  Result.success();
    }


    @GetMapping("/selectType/{id}")
    @ApiOperation(value = "根据分类id查询分类名称")
    public Result<String> SelectType(@PathVariable Integer id){
        String name = bossService.selectType(id);
        return Result.success(name);
    }


    @PostMapping("/addType")
    @ApiOperation(value = "增加商品类型")
    public Result addType(@RequestBody ClassDto classDto){
        bossService.addType(classDto);
        return Result.success();
    }

    @PostMapping("/selectClassification")
    @ApiOperation(value = "查询商品分类表")
    public Result<List> selectClassification(){
        List<ClassificationVo> list = bossService.selectClass();
        return Result.success(list);
    }

//    @PostMapping("/idAndName")
//    @ApiOperation(value = "返回id与name")
    public Result<ClassificationVo> getidname(){
        List<ClassificationVo> data = bossService.getidname();
        return Result.success((ClassificationVo) data);
    }

    /**
     * 老板查询所有超市订单信息
     * @return
     */
    @GetMapping("/shoppingQuery")
    @ApiOperation("老板查询所有超市订单")
    public Result<PageResult> shopQuery(BossShoppingOrderVo bossShoppingOrderVo){
        PageResult pageResult = bossService.shopOrderQuery(bossShoppingOrderVo);
        return Result.success(pageResult);
    }

    /**
     * 老板查询跑腿所有订单
     * @return
     */
    @GetMapping("/takeawayQuery")
    @ApiOperation("老板查询跑腿所有订单")
    public Result<PageResult>  takeawayQuery(BossShoppingOrderVo bossShoppingOrderVo){
        PageResult pageResult = bossService.takeawayOrderQuery(bossShoppingOrderVo);
        return Result.success(pageResult);
    }

    /**
     * 老板搜索商品接口
     * @param commoditySearchDto
     * @return
     */
    @PostMapping("/search")
    @ApiOperation("老板根据商品名称模糊查询商品")
    public  Result<PageResult> searchItem(@RequestBody CommoditySearchDto commoditySearchDto){
        System.out.println(commoditySearchDto);
        PageResult pageResult = bossService.searchItem(commoditySearchDto);
        System.out.println(pageResult);
        return  Result.success(pageResult);
    }

    @ApiOperation("老板确认送达")
    @GetMapping("/confirm/{id}")
    public  Result confirm(@PathVariable Integer id){
        bossService.confirm(id);
        System.out.println(id);
        return Result.success();
    }
}