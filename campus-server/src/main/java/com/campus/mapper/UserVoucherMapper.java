package com.campus.mapper;

import com.campus.entity.Voucher;
import com.campus.entity.VoucherOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserVoucherMapper {
    @Select("select * from voucher where id = #{id}")
    Voucher selectVoucherById(Integer id);

    void saveVoucherOrder(VoucherOrder voucherOrder);

    @Update("update voucher set num = num - 1 where id = #{id}")
    void reduceVoucherNum(Integer id);
}
