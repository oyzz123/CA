package com.campus.controller.boss;

import com.campus.service.boss.BossVoucherService;
import com.campus.dto.VoucherDto;
import com.campus.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/boss/voucher")
@RestController
@Api(tags = "老板优惠券相关接口")
@Slf4j
public class BossVoucherController {
    @Autowired
    private BossVoucherService bossVoucherService;

    /**
     * 新增优惠券
     * @return
     */
    @PostMapping("/1")
    public Result addVoucher(@RequestBody VoucherDto voucherDto) {
        return bossVoucherService.addVoucher(voucherDto);
    }

    /**
     * 删除优惠券（逻辑删除）
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteVoucher(@PathVariable Integer id){
        return bossVoucherService.deleteVoucher(id);
    }

    /**
     * 根据优惠券id查找优惠券
     * @return
     */
    //TODO 查找优惠券
    @GetMapping("/1/{id}")
    public Result selectVoucher(@PathVariable Integer id) {
        return null;
    }

    /**
     * 查找所有未结束的优惠券
     */
    // TODO 查找所有未结束的优惠券
    public Result selectNotEndVoucher() {
        return null;
    }
}
