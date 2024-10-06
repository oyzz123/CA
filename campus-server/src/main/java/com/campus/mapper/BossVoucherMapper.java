package com.campus.mapper;

import com.campus.dto.VoucherDto;
import com.campus.entity.Voucher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BossVoucherMapper {
    void save(VoucherDto voucherDto);

    @Update("update voucher set status = 2 where id = #{id}")
    void delete(int id);

    void update (Voucher voucher);

    @Select("select * from voucher where id = #{id}")
    Voucher selectVoucherById(Integer id);
}
