package com.campus.controller.admin;

import com.campus.service.admin.AdminService;
import com.campus.dto.AdminUpdateDto;
import com.campus.entity.Admin;
import com.campus.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin")
@Api(tags = "管理员管理自我接口")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 管理员查看个人信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("管理员查看个人信息")
    public Result<Admin> Query(@PathVariable int id) {
        Admin admin = adminService.query(id);
        return Result.success(admin);
    }

    /**
     * 管理员修改个人信息
     * @param adminUpdateDto
     * @return
     */
    @ApiOperation("管理员修改个人信息")
    @PutMapping
    public Result update(@RequestBody AdminUpdateDto adminUpdateDto) {
        adminService.update(adminUpdateDto);
        return Result.success();
    }
}
