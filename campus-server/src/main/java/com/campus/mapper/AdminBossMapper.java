package com.campus.mapper;

import com.campus.dto.AdminBossPageDto;
import com.campus.entity.Boss;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminBossMapper {
    /**
     * 管理员分页查询所有老板信息
     * @param adminBossPageDto
     * @return
     */
    Page<Boss> pageQuery(AdminBossPageDto adminBossPageDto);

    /**
     * 管理员根据id查询老板具体信息
     * @param id
     * @return
     */
    @Select("select * from boss where id = #{id}")
    Boss getById(int id);

    /**
     * 管理员修改老板信息
     * @param boss
     */
//    @AutoFill(value = OperationType.UPDATE)
    void update(Boss boss);

    /**
     * 管理员根据id删除老板信息
     * @param id
     */
//    @AutoFill(value = OperationType.UPDATE)
    @Update("update boss set status =0 where id= #{id}")
    void deleteById(int id);

    /**
     * 管理员添加老板
     * @param boss
     */
    @Insert(" insert into boss (nickname, account, password, gender, age, telephone, status, wallet,create_user,create_time,update_user,update_time)\n" +
            "        values (#{nickname},#{account},#{password},#{gender},#{age},#{telephone},#{status},#{wallet},#{createUser},#{createTime},#{updateUser},#{updateTime})")
//    @AutoFill(value = OperationType.INSERT)
    void save(Boss boss);

}
