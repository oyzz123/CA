package com.campus.service.user;

import com.dto.ComplaintsDto;
import com.dto.RechargeDto;
import com.dto.UserDto;
import com.dto.LoginDto;
import com.entity.User;

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
