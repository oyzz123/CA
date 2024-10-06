package com.campus.service.admin;

import com.campus.dto.AdminBossPageDto;
import com.campus.dto.AdminBossSaveDto;
import com.campus.dto.AdminBossUpdateDto;
import com.campus.entity.Boss;
import com.campus.result.PageResult;

public interface AdminBossService {
    /**
     * 管理员分页查询所有老板信息
     * @param adminBossPageDto
     * @return
     */
    PageResult pageQuery(AdminBossPageDto adminBossPageDto);


    /**
     * 管理员根据id查询老板信息
     * @param id
     * @return
     */
    Boss getById(int id);

    /**
     * 管理员修改老板信息
     * @param adminBossUpdateDto
     */
    void update(AdminBossUpdateDto adminBossUpdateDto);

    /**
     * 管理员删除老板信息
     * @param id
     */
    void deleteById(int id);

//    /**
//     * 管理员删除已注销老板信息
//     */
//    void deregister();

    /**
     * 管理员添加老板信息
     * @param adminBossSaveDto
     */
    void save(AdminBossSaveDto adminBossSaveDto);
}
