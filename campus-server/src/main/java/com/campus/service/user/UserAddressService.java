package com.campus.service.user;


import com.campus.dto.UserAddressDto;
import com.campus.entity.UserAddress;

import java.util.ArrayList;

public interface UserAddressService {
    void addAddress(UserAddressDto userAddressDto);

    void modify(UserAddressDto userAddressDto);

    ArrayList<UserAddress> getAllAddress(Integer userId);

    void setDefaultAddress(Integer id, Integer userId);

    UserAddress getDefaultAddress(Integer userId);

    void delete(Integer id);

    void modifyDefaultAddress(Integer id, Integer userId);

    UserAddress getAddress(Integer id);

}
