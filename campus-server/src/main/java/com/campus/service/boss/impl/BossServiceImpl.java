package com.campus.service.boss.impl;

import cn.hutool.json.JSONUtil;
import com.campus.service.boss.BossService;
import com.campus.mapper.BossMapper;
import com.dto.*;
import com.entity.*;
import com.exception.AccountNotFoundException;
import com.exception.AccountStatusException;
import com.exception.CommodityNotFound;
import com.exception.PasswordErrorException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.result.PageResult;
import com.vo.ClassificationVo;
import com.vo.ShopOrderVo;
import com.vo.TakeawayOrderVo;
import com.vo.UserOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BossServiceImpl implements BossService {
    @Resource
    private BossMapper bossMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Boss login(LoginDto loginDto) {
        Boss boss = bossMapper.getBossByAccount(loginDto.getAccount());
        if (boss == null) {
            //抛出异常
            throw new AccountNotFoundException("账号不存在");
        }
        String password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());

        if (!password.equals(boss.getPassword())) {
            //抛出异常
            throw new PasswordErrorException("密码错误");
        }
        if (boss.getStatus() == 2) {
            //抛出异常
            throw new AccountStatusException("账号状态异常");
        }
        return boss;
    }

    @Override
    public void UpdateBoss(UpdateBossDto updateBossDto) {
        Boss boss = new Boss();
        if(updateBossDto.getPassword() != null) {
            String password = DigestUtils.md5DigestAsHex(updateBossDto.getPassword().getBytes());
            System.out.println(password);
            boss.setPassword(password);
        }
        boss.setId(1);
        boss.setNickname(updateBossDto.getNickname());
        boss.setTelephone(updateBossDto.getTelephone());
        boss.setGender(updateBossDto.getGender());
        System.out.println(boss);
        bossMapper.UpdateBoss(boss);
    }

    @Override
    public String selectType(Integer id) {
        String name = bossMapper.selectType(id);
        return name;
    }


    @Override
    public void addType(ClassDto classDto) {
        bossMapper.addType(classDto);
    }

    @Override
    public List<ClassificationVo> selectClass() {
        List<ClassificationVo> res =  bossMapper.ClassPageQuery();
        return res;
    }

    @Override
    public Double findWallet(Integer id) {
        Double money = bossMapper.findWallet(id);
        return money;
    }

    /**
     * 删除商品信息
     * @param id
     */
    @Override
    public void delete(Integer id) {
        Commodity commodity = bossMapper.getById(id);
        if(id == null) {
            throw new CommodityNotFound("商品不存在");
        }else{
            bossMapper.delete(id);
            stringRedisTemplate.delete("cache:commodityVo:" + id);
        }
    }

    /**
     * 新增商品
     * @param commodityDto
     */
    @Override
    public void Insert(CommodityDto commodityDto) {
        CommodityTest commodityTest = new CommodityTest();
        BeanUtils.copyProperties(commodityDto, commodityTest);
        commodityTest.setStatus(1);
        bossMapper.InsertCommodity(commodityTest);
    }

    /**
     * 更新商品信息
     * @param commodityDto
     */
    @Override
    public void Update(CommodityDto commodityDto) throws InterruptedException {
        Commodity commodity = bossMapper.getById(commodityDto.getId());
        if(commodity == null) {
            throw new CommodityNotFound("商品不存在");
        }
        //延迟双删
        stringRedisTemplate.delete("cache:commodity:" + commodityDto.getId());

        bossMapper.Update(commodityDto);

        Thread.sleep(10);
        stringRedisTemplate.delete("cache:commodity:" + commodityDto.getId());
    }


    @Override
    public void Refund(Integer id) {
        Double money = bossMapper.Getprice(id);
        log.info("退款金额为：{}",money);
        bossMapper.Decrease(money);
        bossMapper.Increase(money);
        bossMapper.updateUserOrderStatus(id);
    }

    /**
     * 查询商品信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Commodity SelectById(Integer id) {
        String commodityJson = stringRedisTemplate.opsForValue().get("cache:commodity:" + id);
        //缓存命中
        if(StrUtil.isNotBlank(commodityJson)) {
            return JSONUtil.toBean(commodityJson, Commodity.class);
        }
        //查询商品信息
        Commodity commodity = bossMapper.getById(id);
        //加入缓存
        int randomTime = (int)Math.random() * 10;
        stringRedisTemplate.opsForValue().set("cache:commodity:" + id,JSONUtil.toJsonStr(commodity),30 + randomTime, TimeUnit.MINUTES);

        return commodity;
    }

    @Override
    public PageResult pageQuery(CommodityPageQueryDto commodityPageQueryDto) {
        PageHelper.startPage(commodityPageQueryDto.getPage(), commodityPageQueryDto.getPageSize());
        Page<Commodity> page = bossMapper.pageQuery(commodityPageQueryDto);
        long total = page.getTotal();
        List<Commodity> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    public void processOrder(List<OrderCommodityVo> orderCommodityVo) {
        for (OrderCommodityVo order : orderCommodityVo) {
            Integer num = order.getNum();
            Integer id = order.getCommodityId();
            bossMapper.deAllowance(id, num);
        }
    }

    @Override
    public double SelectMonth(Integer year, Integer month) {
        String startDate = year + "-" + String.format("%02d", month) + "-01";
        String endDate = year + "-" + String.format("%02d", month) + "-31";
        return bossMapper.SelectMonth(startDate, endDate);
    }

    @Override
    public double SelectYear(Integer year) {
        String startDate = year + "-01-01";
        String endDate = year + "-12-31";

        // 查询给定时间范围内的所有交易金额
        List<Double> amounts = bossMapper.SelectYear(startDate, endDate);

        // 计算总金额
        double totalAmount = 0;
        for (Double amount : amounts) {
            totalAmount += amount;
        }

        return totalAmount;
    }

    @Override
    public PageResult UserPageQuery(UserPageQueryDto userPageQueryDto) {
        PageHelper.startPage(userPageQueryDto.getPage(), userPageQueryDto.getPageSize());
        Page<UserOrderVo> page = bossMapper.UserPageQuery(userPageQueryDto);
        long total = page.getTotal();
        List<UserOrderVo> records = page.getResult();
        return new PageResult(total, records);
    }

    /**
     * 老板确认超市商品送达
     * @param id
     */
    @Override
    public void confirm(Integer id) {
        bossMapper.confirm(id);
    }

    @Override
    public PageResult riderPageQuery(UserPageQueryDto userPageQueryDto) {
        PageHelper.startPage(userPageQueryDto.getPage(), userPageQueryDto.getPageSize());
        Page<UserOrderVo> page = bossMapper.RiderPageQuery(userPageQueryDto);
        long total = page.getTotal();
        List<UserOrderVo> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    public void Refund2(Integer orderId) {
        System.out.println("订单id" + orderId);
        Integer riderId = bossMapper.findUserIdByOrderId(orderId);
        System.out.println("骑手id" + orderId);
        bossMapper.updateUserOrderStatus(orderId);
        bossMapper.AddForRider(riderId);
    }


    @Override
    public List<com.vo.ClassificationVo> getidname() {
        return bossMapper.getidname();
    }

    /**
     * 老板分页查询所有超市订单
     * @return
     */
    @Override
    public PageResult shopOrderQuery(BossShoppingOrderVo bossShoppingOrderVo) {
        PageHelper.startPage(bossShoppingOrderVo.getPage(),bossShoppingOrderVo.getPagesize());
        Page<ShopOrderVo> page = bossMapper.shopOrderQuery(bossShoppingOrderVo);
        long total = page.getTotal();
        List<ShopOrderVo> records = page.getResult();
        System.out.println(records);
        return new PageResult(total, records);
    }


    /**
     * 老板查询所有跑腿订单
     * @return
     */
    @Override
    public PageResult takeawayOrderQuery(BossShoppingOrderVo bossShoppingOrderVo) {
        PageHelper.startPage(bossShoppingOrderVo.getPage(),bossShoppingOrderVo.getPagesize());
        Page<TakeawayOrderVo> page = bossMapper.takeawayOrderQuery(bossShoppingOrderVo);
        long total = page.getTotal();
        List<TakeawayOrderVo> records = page.getResult();
        return new PageResult(total, records);
    }

    /**
     * 拒绝退款自动完成订单
     * @param orderId
     */
    @Override
    public void notRefund(Integer orderId) {
        bossMapper.notRefund(orderId);
    }

    /**
     * 根据名称模糊查询商品
     * @param commoditySearchDto
     * @return
     */
    @Override
    public PageResult searchItem(CommoditySearchDto commoditySearchDto) {
        PageHelper.startPage(commoditySearchDto.getPage(),commoditySearchDto.getPageSize());
        Page<Commodity> page = bossMapper.searchItem(commoditySearchDto.getCommodityName());
        long total = page.getTotal();
        List<Commodity> records = page.getResult();
        return new PageResult(total, records);
    }
}