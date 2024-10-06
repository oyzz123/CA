package com.campus.controller.user;

import com.campus.service.user.impl.UserServiceImpl;
import com.campus.dto.RechargeDto;
import com.campus.dto.UserDto;
import com.campus.entity.User;
import com.campus.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户相关接口")
@CrossOrigin
public class UserController {
    @Autowired
    private UserServiceImpl userService;


    /**
     * 用户注册
     * @param userDto
     * @return
     */
    @ApiOperation("注册")
    @PostMapping("/register")
    public Result Register (@RequestBody UserDto userDto){
        log.info("用户注册账号...");
        userService.register(userDto);
        User user = userService.selectUserId(userDto.getAccount());
        System.out.println(user.getId());
        return Result.success();
    }

    /**
     * 用户更新个人信息
     * @param userDto
     * @return
     */
    @PutMapping
    @ApiOperation("用户更新个人信息")
    public Result update(@RequestBody UserDto userDto){
        userService.update(userDto);

        return Result.success();
    }

    /**
     * 用户注销账号
     * @param id
     * @return
     */
    @ApiOperation("注销账号")
    @DeleteMapping("/{id}")
    public Result deregister(@PathVariable int id){
        userService.deregister(id);
        return Result.success();
    }


    /**
     * 查看钱包
     * @param id
     * @return
     */
    @ApiOperation("查看钱包")
    @GetMapping("/{id}")
    public Result<Double> showWallet(@PathVariable int id){
        double result = userService.selectWallet(id);
        return Result.success(result);
    }


    /**
     * 根据用户id以及金额修改用户钱包余额
     * @param rechargeDto
     * @return
     */
    @ApiOperation("用户充值钱包")
    @PutMapping("/recharge")
    public Result recharge(@RequestBody RechargeDto rechargeDto){
        log.info("数据为：{}",rechargeDto);
        userService.recharge(rechargeDto);
        return Result.success();
    }


    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/select/{id}")
    @ApiOperation("根据用户id查询用户信息")
    public Result findUserById(@PathVariable Integer id){
        log.info("查询用户{}信息...",id);
        User user = userService.selectUserById(id);
        return Result.success(user);
    }


}
