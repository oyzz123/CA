package com.campus.service.boss;

import com.campus.dto.VoucherDto;
import com.campus.exception.VoucherException;
import com.campus.result.Result;

public interface BossVoucherService {
    /**
     * 新增优惠券
     * @param voucherDto
     * @return
     */
    Result addVoucher(VoucherDto voucherDto);

    /**
     * 新增优惠券
     * @param id
     * @return
     */
    Result deleteVoucher(Integer id) throws VoucherException;
}
