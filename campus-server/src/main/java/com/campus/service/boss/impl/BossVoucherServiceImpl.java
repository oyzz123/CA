package com.campus.service.boss.impl;

import com.campus.mapper.BossVoucherMapper;
import com.campus.service.boss.BossVoucherService;
import com.dto.VoucherDto;
import com.entity.Voucher;
import com.exception.VoucherException;
import com.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 优惠券类型码
 * 1.普通券
 * 2.秒杀券
 */
@Service
@Slf4j
public class BossVoucherServiceImpl implements BossVoucherService {
    @Autowired
    private BossVoucherMapper bossVoucherMapper;

    /**
     * 新增优惠券
     * @param voucherDto
     * @return
     */
    @Override
    public Result addVoucher(VoucherDto voucherDto) {
        bossVoucherMapper.save(voucherDto);
        return Result.success();
    }

    /**
     * 删除优惠券
     * @param id
     * @return
     */
    @Override
    public Result deleteVoucher(Integer id) {
        //查找优惠券
        Voucher voucher = bossVoucherMapper.selectVoucherById(id);
        //不存在抛出异常
        if(voucher == null) {
            throw new VoucherException("优惠券无法找到");
        }
        //存在删除
        bossVoucherMapper.delete(id);
        return Result.success();
    }
}
