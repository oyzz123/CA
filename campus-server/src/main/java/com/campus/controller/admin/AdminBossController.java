package com.campus.controller.admin;

import com.campus.service.admin.AdminBossService;
import com.campus.service.admin.impl.AdminBossServiceImpl;
import com.dto.AdminBossPageDto;
import com.dto.AdminBossSaveDto;
import com.dto.AdminBossUpdateDto;
import com.entity.Boss;
import com.result.PageResult;
import com.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/boss")
@Slf4j
@Api(tags = "管理员老板接口")
@CrossOrigin
public class AdminBossController {
    private final AdminBossService adminBossService;

    public AdminBossController(AdminBossServiceImpl adminBossServiceImpl) {
        this.adminBossService = adminBossServiceImpl;
    }

    /**
     * 管理员分页查询所有老板信息
     * @param adminBossPageDto
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("管理员分页查询所有老板信息")
    public Result<PageResult> getPage(AdminBossPageDto adminBossPageDto){
        PageResult pageResult = adminBossService.pageQuery(adminBossPageDto);
        return Result.success(pageResult);
    }

    /**
     *管理员根据id查询老板信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("管理员根据id查询老板信息")
    public Result<Boss> getById(@PathVariable int id){
        Boss boss= adminBossService.getById(id);
        return Result.success(boss);
    }

    /**
     * 管理员修改老板信息
     * @param adminBossUpdateDto
     * @return
     */
    @PutMapping
    @ApiOperation("管理员修改老板信息")
    public Result updateUser(@RequestBody AdminBossUpdateDto adminBossUpdateDto){
        adminBossService.update(adminBossUpdateDto);
        return Result.success();
    }

    /**
     * 管理员删除老板信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation("管理员删除老板信息")
    public Result deleteById(@PathVariable int id){
       adminBossService.deleteById(id);
        return Result.success();
    }

    /**
     * 管理员新增老板信息
     * @param adminBossSaveDto
     * @return
     */
    @PostMapping
    @ApiOperation("管理员新增老板信息")
    public Result save(@RequestBody AdminBossSaveDto adminBossSaveDto){
        adminBossService.save(adminBossSaveDto);
        return Result.success();
    }
}
