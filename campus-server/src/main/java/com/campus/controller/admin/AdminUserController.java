package com.campus.controller.admin;

import com.campus.service.admin.AdminUserService;
import com.campus.service.admin.impl.AdminUserServiceImpl;
import com.dto.AdminUserPageDto;
import com.dto.AdminUserSaveDto;
import com.dto.AdminUserUpdateDto;
import com.entity.User;
import com.result.PageResult;
import com.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@Slf4j
@Api(tags = "管理员管理用户接口")
@CrossOrigin
public class AdminUserController {
  @Autowired
  private AdminUserService adminUserService;

  /**
   * 管理员分页查询所有用户信息
   * @param adminUserPageDto
   * @return
   */
  @GetMapping("/page")
  @ApiOperation("管理员分页查询所有用户信息")
  public Result<PageResult> getPage(AdminUserPageDto adminUserPageDto){
    PageResult pageResult = adminUserService.pageQuery(adminUserPageDto);
    return Result.success(pageResult);
  }

  /**
   *管理员根据id查询用户信息
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  @ApiOperation("管理员根据id查询用户信息")
  public Result<User> getById(@PathVariable int id){
    User user= adminUserService.getById(id);
    return Result.success(user);
  }

  /**
   * 管理员修改员工信息
   * @param userDto
   * @return
   */
  @PutMapping
  @ApiOperation("管理员修改用户信息")
  public Result updateUser(@RequestBody AdminUserUpdateDto userDto){
     adminUserService.update(userDto);
     return Result.success();
  }

  /**
   * 管理员删除用户信息
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  @ApiOperation("管理员删除用户信息")
  public Result delete(@PathVariable int id){
    adminUserService.deleteById(id);
    return Result.success();
  }

  /**
   * 管理员删除已注销用户信息
   * @return
   */
  @DeleteMapping()
  @ApiOperation("管理员删除已注销用户信息")
  public  Result deregister(){
    adminUserService.deregister();
    return Result.success();
  }

  /**
   * 管理员新增用户信息
   * @param adminUserSaveDto
   * @return
   */
  @PostMapping
  @ApiOperation("管理员新增用户信息")
  public Result save(@RequestBody AdminUserSaveDto adminUserSaveDto){
    adminUserService.save(adminUserSaveDto);
    return Result.success();
  }


}
