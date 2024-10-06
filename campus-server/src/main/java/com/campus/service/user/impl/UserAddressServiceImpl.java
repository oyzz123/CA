package com.campus.service.user.impl;

import com.campus.mapper.UserAddressMapper;
import com.campus.service.user.UserAddressService;
import com.dto.UserAddressDto;
import com.entity.UserAddress;
import com.exception.TelephoneException;
import com.vo.UserAddressVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 用户新增地址
     * @param userAddressDto
     */
    @Override
    public void addAddress(UserAddressDto userAddressDto) {
        UserAddressVo userAddressVo = new UserAddressVo();
        BeanUtils.copyProperties(userAddressDto,userAddressVo);
        String userTelephone = userAddressVo.getUserTelephone();
        if(!isValid(userTelephone)){
            throw new TelephoneException("电话号码输入有误，请重新输入");
        }
        userAddressVo.setStatus(1);
        userAddressVo.setCreateUser(userAddressDto.getUserId());
        userAddressVo.setCreateTime(LocalDateTime.now());
        userAddressMapper.addAddress(userAddressVo);
    }

    /**
     * 用户修改地址信息
     * @param userAddressDto
     */
    @Override
    public void modify(UserAddressDto userAddressDto) {
        UserAddressVo userAddressVo = new UserAddressVo();
        String userTelephone = userAddressDto.getUserTelephone();
        if(!isValid(userTelephone)){
            throw new TelephoneException("电话号码输入有误，请重新输入");
        }
        BeanUtils.copyProperties(userAddressDto,userAddressVo);
        userAddressVo.setStatus(1);
        userAddressVo.setUpdateUser(userAddressDto.getUserId());
        userAddressVo.setUpdateTime(LocalDateTime.now());
        userAddressMapper.modifyAddress(userAddressVo);
    }

    /**
     * 查看用户现存所有地址
     * @param userId
     * @return
     */
    @Override
    public ArrayList<UserAddress> getAllAddress(Integer userId) {
        return userAddressMapper.getAllAddress(userId);
    }

    /**
     * 设置默认地址
     * @param id
     * @param userId
     */
    @Override
    public void setDefaultAddress(Integer id, Integer userId) {
        userAddressMapper.setDefaultAddress(id, userId);
    }

    /**
     * 获得默认地址
     * @param userId
     * @return
     */
    @Override
    public UserAddress getDefaultAddress(Integer userId) {
        return userAddressMapper.getDefaultAddress(userId);
    }

    /**
     * 修改默认地址
     * @param id
     * @param userId
     */
    @Override
    public void modifyDefaultAddress(Integer id, Integer userId) {
        UserAddress startDefaultAddress = userAddressMapper.getDefaultAddress(userId);
        userAddressMapper.modifyDefaultAddress(startDefaultAddress.getId());
        userAddressMapper.setDefaultAddress(id, userId);
    }

    /**
     * 删除地址
     * @param id
     */
    @Override
    public void delete(Integer id) {
        userAddressMapper.delete(id);
    }

    /**
     * 判断电话号码是否合法（简易版）
     * @param telephone
     * @return
     */
    public boolean isValid(String telephone){
        if(telephone.length() == 11 && telephone.charAt(0) == '1'){
            return true;
        }
        return false;
    }

    /**
     * 根据id获得地址
     * @param id
     * @return
     */
    @Override
    public UserAddress getAddress(Integer id) {
        return userAddressMapper.getAddress(id);
    }
}
