package com.campus.service.user;

import com.campus.dto.ShopCartDto;
import com.campus.result.Result;

public interface ShopCartService {
    Result addCommodity(ShopCartDto shopCartDto);

    Result selectAll(Integer userId);

    Result deleteAll(Integer id);

    Result deleteCommodity(Integer userId, Integer cId);
}
