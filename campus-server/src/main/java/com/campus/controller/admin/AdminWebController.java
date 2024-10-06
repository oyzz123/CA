package com.campus.controller.admin;

import com.campus.service.admin.AdminWebService;
import com.campus.result.Result;
import com.campus.vo.AdminQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/admin/web")
@Slf4j
@Api(tags = "管理员管理网站接口")
@CrossOrigin
public class AdminWebController {
    @Autowired
    private AdminWebService adminWebService;

    /**
     * 管理员查询网站月总流水
     * @param year
     * @param month
     * @return
     */
    @GetMapping("/month/{year}/{month}")
    @ApiOperation("管理员查询网站月总流水")
    public Result<AdminQueryVo> sumMonth(@PathVariable int year, @PathVariable int month) {
        AdminQueryVo adminQueryVo = adminWebService.monthSum(year, month);
        return Result.success(adminQueryVo);
    }


    /**
     * 管理员查询网站年流水
     * @param year
     * @return
     */
    @GetMapping("/year/{year}")
    @ApiOperation("管理员查询网站年总流水")
    public Result<ArrayList<AdminQueryVo>> yearSumQuery(@PathVariable int year) {
        ArrayList<AdminQueryVo> queryList = adminWebService.yearSumQuery(year);
        return Result.success(queryList);
    }
}
