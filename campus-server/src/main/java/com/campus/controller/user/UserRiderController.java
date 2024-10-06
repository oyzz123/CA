package com.campus.controller.user;

import com.campus.service.user.UserRiderService;
import com.dto.OrderPageDto;
import com.dto.RiderAcceptOrderDto;
import com.dto.RiderRefundOrderDto;
import com.result.PageResult;
import com.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/rider")
@Slf4j
@Api(tags = "骑手相关接口")
@CrossOrigin
public class UserRiderController {
    @Autowired
    private UserRiderService userRiderService;

    /**
     * 骑手查看所有可接单订单
     * @param orderPageDto
     * @return
     */
    @ApiOperation("骑手查看所有可接单订单")
    @GetMapping("/page")
    public Result<PageResult> showOrder(OrderPageDto orderPageDto){
        log.info("骑手查看所有可接单订单");
        PageResult pageResult = userRiderService.showOrder(orderPageDto);
        return Result.success(pageResult);
    }

    /**
     * 骑手选择订单接单
     * @param riderAcceptOrderDto
     * @return
     */
    @PutMapping
    @ApiOperation("骑手接单")
    public Result acceptOrder(@RequestBody RiderAcceptOrderDto riderAcceptOrderDto){
        userRiderService.acceptOrder(riderAcceptOrderDto);
        return Result.success();
    }

    /**
     * 查看个人所有接单订单信息
     * @return
     */
    @GetMapping("/1")
    @ApiOperation("展示个人所有接单订单信息")
    public Result<PageResult> showRiderOrder(OrderPageDto orderPageDto){
        log.info("查看个人所有接单订单信息");
        PageResult pageResult = userRiderService.showRiderOrder(orderPageDto);
        return Result.success(pageResult);
    }

    /**
     * 查看骑手收益
     * @param id
     * @return
     */
    @ApiOperation("查看骑手收益")
    @GetMapping("/2/{id}")
    public Result<Double> showRiderWallet(@PathVariable int id){
        return Result.success(userRiderService.showRiderWallet(id));
    }

    /**
     * 提现骑手钱包
     * @param id
     * @return
     */
    @ApiOperation("提现骑手钱包")
    @PutMapping("/{id}")
    public Result withDrawals(@PathVariable int id){
        userRiderService.WithDrawals(id);
        return Result.success();
    }

    @ApiOperation("骑手投诉订单")
    @PostMapping()
    public Result riderRefundOrder(@RequestBody RiderRefundOrderDto riderRefundOrderDto){
        userRiderService.riderRefundOrder(riderRefundOrderDto);
        return Result.success();
    }

    /**
     * 骑手确认送达
     * @param orderId
     * @return
     */
    @ApiOperation("骑手确认送达")
    @GetMapping("/3/{orderId}")
    public Result riderConfirm(@PathVariable Integer orderId){
        userRiderService.confirm(orderId);
        return Result.success();
    }
}
