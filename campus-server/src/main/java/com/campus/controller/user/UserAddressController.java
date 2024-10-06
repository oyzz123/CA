package com.campus.controller.user;

import com.campus.service.user.UserAddressService;
import com.dto.UserAddressDto;
import com.entity.UserAddress;
import com.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user/address")
@Slf4j
@Api(tags = "用户地址相关接口")
@CrossOrigin
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    /**
     * 用户新增地址
     * @param userAddressDto
     * @return
     */
    @ApiOperation("新增地址")
    @PostMapping()
    public Result addAddress(@RequestBody UserAddressDto userAddressDto){
        userAddressService.addAddress(userAddressDto);
        return Result.success();
    }

    /**
     * 用户修改地址信息
     * @param userAddressDto
     * @return
     */
    @ApiOperation("修改地址")
    @PutMapping()
    public Result modifyAddress(@RequestBody UserAddressDto userAddressDto){
        userAddressService.modify(userAddressDto);
        return Result.success();
    }

    /**
     * 查看用户现存所有地址
     * @param userId
     * @return
     */
    @ApiOperation("查看该用户所有地址信息")
    @GetMapping("/all/{userId}")
    public Result<ArrayList<UserAddress>> getAllAddress(@PathVariable Integer userId){
        return Result.success(userAddressService.getAllAddress(userId));
    }

    /**
     * 设置默认地址
     * @param id
     * @param userId
     * @return
     */
    @ApiOperation("设置默认地址")
    @GetMapping("/set/{id}/{userId}")
    public Result setDefaultAddress(@PathVariable Integer id, @PathVariable Integer userId){
        userAddressService.setDefaultAddress(id, userId);
        return Result.success();
    }

    /**
     * 用户获得默认地址
     * @param userId
     * @return
     */
    @ApiOperation("获取默认地址")
    @GetMapping("/get/{userId}")
    public Result<UserAddress> getDefaultAddress(@PathVariable Integer userId){
        return Result.success(userAddressService.getDefaultAddress(userId));
    }

    /**
     * 修改默认地址
     * @param id
     * @param userId
     * @return
     */
    @ApiOperation("修改默认地址")
    @PutMapping("/modify/{id}/{userId}")
    public Result modifyDefaultAddress(@PathVariable Integer id,@PathVariable Integer userId){
        userAddressService.modifyDefaultAddress(id, userId);
        return Result.success();
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @ApiOperation("删除地址")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        userAddressService.delete(id);
        return Result.success();
    }

    /**
     * 根据id获得地址
     * @param id
     * @return
     */
    @ApiOperation("获得地址根据id")
    @GetMapping("/get2/{id}")
    public Result<UserAddress> getAddress(@PathVariable Integer id){
        return Result.success(userAddressService.getAddress(id));
    }
}
