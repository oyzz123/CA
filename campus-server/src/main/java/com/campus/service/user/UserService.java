package com.campus.service.user;

import com.campus.dto.RechargeDto;
import com.campus.dto.UserDto;
import com.campus.dto.LoginDto;
import com.campus.entity.User;

public interface UserService {
    User login(LoginDto loginDto);

    void update(UserDto userDto);

    void deregister(Integer id);


     Double selectWallet(Integer id);

    void register(UserDto userDto);

    void recharge(RechargeDto rechargeDto);

    User selectUserId(String account);

    User selectUserById(Integer id);
}
