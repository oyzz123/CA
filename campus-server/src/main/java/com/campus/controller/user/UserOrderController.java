package com.campus.controller.user;

import com.campus.service.user.UserOrderService;
import com.dto.OrderPageDto;
import com.dto.RefundOrderDto;
import com.dto.UserOrderDto;
import com.result.PageResult;
import com.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "用户找跑腿相关接口")
@CrossOrigin
public class UserOrderController
{
    @Autowired
    private UserOrderService userOrderService;

    @PostMapping
    @ApiOperation("提交跑腿订单")
    public Result submit(@RequestBody UserOrderDto userOrderDto){
        Integer id = userOrderService.submit(userOrderDto);
        return Result.success(id);
    }

    @ApiOperation("查询订单状态")
    @GetMapping("/{id}")
    public Result<Integer> showOrderStatus(@PathVariable Integer id){
        return Result.success(userOrderService.showOrderStatus(id));
    }

    @ApiOperation("用户确认收货")
    @PutMapping("/{id}")
    public Result ConfirmOrder(@PathVariable Integer id){
        System.out.println(id);
        userOrderService.ConfirmOrder(id);
        return Result.success();
    }

    @ApiOperation("用户申请退款")
    @PostMapping("/refund")
    public Result<Integer> RefundOrder(@RequestBody RefundOrderDto refundOrderDto){
        log.info("跑腿退款:{}",refundOrderDto);
        userOrderService.refundOrder(refundOrderDto);
        return Result.success();
    }

    @ApiOperation("查看个人跑腿订单")
    @GetMapping("/showOurself")
    public Result<PageResult> showOrder(OrderPageDto orderPageDto){
        PageResult pageResult = userOrderService.showOrder(orderPageDto);
        return Result.success(pageResult);
    }
}
