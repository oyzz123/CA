package com.campus.service.user;

import com.dto.ShopCartDto;
import com.result.Result;

public interface ShopCartService {
    Result addCommodity(ShopCartDto shopCartDto);

    Result selectAll(Integer userId);

    Result deleteAll(Integer id);

    Result deleteCommodity(Integer userId, Integer cId);
}
