package com.campus.controller.user;

import com.campus.service.user.UserVoucherService;
import com.campus.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user/voucher")
@RestController
@Api(tags = "用户优惠券相关接口")
@Slf4j
@CrossOrigin
public class UserVoucherController {

    @Autowired
    private UserVoucherService userVoucherService;

    /**
     * 购买普通优惠券
     * @return
     */
    @PostMapping("/1/{id}")
    public Result getVoucher(@PathVariable Integer id) {
        // TODO 购买普通优惠券
        String name = Thread.currentThread().getName();
        Integer userId = Integer.valueOf(name);
        userVoucherService.getVoucher(userId,id);
        return Result.success();
    }

    /**
     * 抢购秒杀券
     * @return
     */
    @PostMapping("/2/{id}")
    public Result killVoucher(@PathVariable Integer id) throws InterruptedException {
        //TODO 抢购优惠券
        String name = Thread.currentThread().getName();
        Integer userId = Integer.valueOf(name);
        userVoucherService.killVoucher(userId,id);
        return Result.success();
    }
}
