package com.campus.service.admin.impl;

import com.campus.mapper.AdminBossMapper;
import com.campus.service.admin.AdminBossService;
import com.constant.PasswordConstant;
import com.dto.AdminBossPageDto;
import com.dto.AdminBossSaveDto;
import com.dto.AdminBossUpdateDto;
import com.entity.Boss;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.result.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBossServiceImpl implements AdminBossService {

    @Autowired
    AdminBossMapper adminBossMapper;

    /**
     * 管理员分页查询所有老板信息
     * @param adminBossPageDto
     * @return
     */
    @Override
    public PageResult pageQuery(AdminBossPageDto adminBossPageDto) {
        PageHelper.startPage(adminBossPageDto.getPage(),adminBossPageDto.getPageSize());
        Page<Boss> page = adminBossMapper.pageQuery(adminBossPageDto);
        long total = page.getTotal();
        List<Boss> result = page.getResult();
        return new PageResult(total,result);
    }


    /**
     * 管理员根据id查询老板信息
     * @param id
     * @return
     */
    @Override
    public Boss getById(int id) {
        Boss boss = adminBossMapper.getById(id);
        return boss;
    }

    /**
     * 管理员修改老板信息
     * @param adminBossUpdateDto
     */
    @Override
    public void update(AdminBossUpdateDto adminBossUpdateDto) {
        Boss boss = new Boss();
        BeanUtils.copyProperties(adminBossUpdateDto,boss);
        adminBossMapper.update(boss);
    }

    /**
     * 管理员根据Id删除老板信息
     * @param id
     */
    @Override
    public void deleteById(int id) {
        adminBossMapper.deleteById(id);
    }


    /**
     * 管理员添加老板
     * @param adminBossSaveDto
     */
    @Override
    public void save(AdminBossSaveDto adminBossSaveDto) {
        Boss boss = new Boss();
        BeanUtils.copyProperties(adminBossSaveDto,boss);
        boss.setPassword(PasswordConstant.PASSWORD);
        adminBossMapper.save(boss);
    }
}
