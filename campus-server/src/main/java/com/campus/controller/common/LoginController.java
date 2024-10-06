package com.campus.controller.common;

import com.campus.service.admin.AdminService;
import com.campus.service.boss.BossService;
import com.campus.service.user.UserService;
import com.dto.LoginDto;
import com.entity.Admin;
import com.entity.Boss;
import com.entity.User;
import com.result.Result;
import com.utils.JwtUtil;
import com.vo.AdminLoginVo;
import com.vo.BossLoginVo;
import com.vo.UserLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "用户登录")
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private BossService bossService;
    @Autowired
    private AdminService adminService;


    @GetMapping("/1")
    @ApiOperation("用户登录验证")
    public Result userLogin(LoginDto loginDto) {
        log.info("用户登录：{}", loginDto);
        User user = userService.login(loginDto);
        if(user == null) {
            return Result.error("用户名或密码错误");
        }

        String token = JwtUtil.createToken(user.getId().toString());

        UserLoginVo userLoginVo = UserLoginVo.builder()
                .id(user.getId())
                .account(user.getAccount())
                .name(user.getNickname())
                .token(token)
                .build();
        return Result.success(userLoginVo);
    }

    @GetMapping("/2")
    @ApiOperation("管理员登录验证")
    public Result adminLogin(LoginDto loginDto) {
        log.info("管理员登录：{}", loginDto);
        Admin admin = adminService.login(loginDto);
        if(admin == null) {
            return Result.error("用户名或密码错误");
        }

        String token = JwtUtil.createToken(admin.getId().toString());

        AdminLoginVo adminLoginVo = AdminLoginVo.builder()
                .id(admin.getId())
                .account(admin.getAccount())
                .name(admin.getNickname())
                .token(token)
                .build();
        return Result.success(adminLoginVo);
    }

    @GetMapping("/3")
    @ApiOperation("老板登录验证")
    public Result bossLogin(LoginDto loginDto) {
        log.info("用户登录：{}", loginDto);
        Boss boss = bossService.login(loginDto);
        if(boss == null) {
            return Result.error("用户名或密码错误");
        }

        String token = JwtUtil.createToken(boss.getId().toString());

        BossLoginVo bossLoginVo = BossLoginVo.builder()
                .id(boss.getId())
                .account(boss.getAccount())
                .name(boss.getNickname())
                .token(token)
                .build();
        return Result.success(bossLoginVo);
    }

}
