package com.campus.service.user.impl;

import cn.hutool.json.JSONUtil;
import com.campus.dto.*;
import com.campus.entity.*;
import com.campus.mapper.UserMapper;
import com.campus.service.user.UserShopService;
import com.campus.mapper.UserShopMapper;
import com.dto.*;
import com.entity.*;
import com.campus.exception.CommodityNotFound;
import com.campus.exception.ShopOrderNotFound;
import com.campus.exception.WalletException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.campus.result.PageResult;
import com.campus.vo.BossVo;
import com.campus.vo.CommodityVo;
import com.campus.vo.UserShopOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserShopServiceImpl implements UserShopService {
    @Autowired
    private UserShopMapper userShopMapper;
    @Autowired
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @Override
    public CommodityVo getCommodityById(Integer id) {
        int randomTime = (int)Math.random() * 10;
        String commodityJson = stringRedisTemplate.opsForValue().get("cache:commodityVo:" + id);

        if(StrUtil.isNotBlank(commodityJson)) {
            //缓存命中
            CommodityVo commodityVo = JSONUtil.toBean(commodityJson,CommodityVo.class);
            return commodityVo;
        }

        //不存在
        CommodityVo good = userShopMapper.getById(id);
        if(good == null) {
            //解决缓存穿透
            stringRedisTemplate.opsForValue().set("cache:commodityVo:" + id, null,30 + randomTime,TimeUnit.MINUTES);
            throw new CommodityNotFound("找不到此商品");
        }

        stringRedisTemplate.opsForValue().set("cache:commodityVo:" + id, JSONUtil.toJsonStr(good), 30 + randomTime,TimeUnit.MINUTES);
        return good;
    }


    /**
     * 支付超市订单
     *
     * @param shopOrderDto
     * @return
     */
    @Override
    @Transactional
    public Integer payCommodity(ShopOrderDto shopOrderDto) {
        if(userMapper.selectWallet(shopOrderDto.getUserId()) < shopOrderDto.getMoney()){
            //用户余额不足以支付订单
            log.info("余额不足");
            throw new WalletException("余额不足");
        }else{
            //余额充足
            ShopOrder shopOrder = new ShopOrder();
            User user = new User();
            user.setId(shopOrderDto.getUserId());
            user.setWallet(shopOrderDto.getMoney());
            userMapper.pay(user);
            BeanUtils.copyProperties(shopOrderDto,shopOrder);
            shopOrder.setStatus(0);//设置状态为未接单
            userShopMapper.save(shopOrder);
            log.info("新增超市订单id为 ： {}" ,shopOrder.getId());
            System.out.println(shopOrder.getId());
            Boss boss = new Boss();
            boss.setId(1);
            boss.setWallet(shopOrderDto.getMoney());
            userShopMapper.updateBossWallet(boss);

            return shopOrder.getId();
        }
    }

    /**
     * 展示老板信息
     * @return
     */
    @Override
    public BossVo showBoss() {
        return userShopMapper.showBoss();
    }

    /**
     * 商品信息分页查询
     * @param commodityPageDto
     * @return
     */
    @Override
    public PageResult showCommodity(CommodityPageDto commodityPageDto) {
        PageHelper.startPage(commodityPageDto.getPage(),commodityPageDto.getPageSize());
        Page<Commodity> page = userShopMapper.showCommodity(commodityPageDto);
        Long total = page.getTotal();
        List<Commodity> records = page.getResult();
        return new PageResult(total,records);
    }

    /**
     * 个性化推荐功能
     * @param recommendDto
     * @return
     */
    @Override
    public List<CommodityVo> recommend(RecommendDto recommendDto) {
        System.out.println("进入service");
        if(userShopMapper.findUserOrder(recommendDto.getUserId()) == 0){
            System.out.println("新用户");
            //新用户
            //找出热门商品推荐给用户
            ArrayList<RecommendNew> num;
            num = userShopMapper.selectCommodityNum();
            int len = num.size();
//            int flag = len >= 20 ? 1 : 0;
            //对商品数量进行降序排列
            Collections.sort(num,(o1, o2) -> {
                return o2.getNum() - o1.getNum();
            });

            //找出热门商品并返回
            ArrayList<CommodityVo> commodities = new ArrayList<>();
            for(int i = 0; i < 20 ; i ++){
                if(i == len) {
                    break;
                }
                //返回二十条数据给前端
                //根据id查找商品信息
                commodities.add(userShopMapper.getById(num.get(i).getCommodityId()));

            }
            List<CommodityVo> list = commodities;
            return list;
        }else{
            //老用户

            //生成用户感兴趣商品类型集合
            HashSet<Integer> type;
            type = userShopMapper.selectCommodityTypeByUserId(recommendDto.getUserId());

            //找出用户感兴趣的商品类型的购买量
            ArrayList<RecommendOld> num = new ArrayList<>();
            for(Integer typeId : type){
                Integer commodityNum = userShopMapper.selectCommodityNumByIdType(typeId,recommendDto.getUserId());
                RecommendOld recommendOld = new RecommendOld(typeId,commodityNum);
                num.add(recommendOld);
            }


            //对集合中的数量进行降序排列
            Collections.sort(num,(o1,o2) -> {
                return o2.getNum() - o1.getNum();
            });


            ArrayList<CommodityVo> result = new ArrayList<>();
            int i = 0;
            ArrayList<RecommendNew> commodityIdNum = new ArrayList<>();

            if(num.size() >= 4) {
                for(RecommendOld recommendOld : num) {
                    if(i == 4) break;
                    commodityIdNum = userShopMapper.selectCommodityNumByType(recommendOld.getTypeId());
                    Collections.sort(commodityIdNum,(o1,o2) -> o2.getNum() - o1.getNum());
                    for(int j = 1; j < 5; j++) {
                        if(commodityIdNum.size() == j)break;
                        result.add(userShopMapper.getById(commodityIdNum.get(j).getCommodityId()));
                    }
                    i++;
                }

            }else{
                for(RecommendOld recommendOld2 : num) {
                    commodityIdNum = userShopMapper.selectCommodityNumByType(recommendOld2.getTypeId());
                    Collections.sort(commodityIdNum,(o1,o2) -> o2.getNum() - o1.getNum());
                    for(int j = 1; j < 10; j++) {
                        if(commodityIdNum.size() == j)break;
                        result.add(userShopMapper.getById(commodityIdNum.get(j).getCommodityId()));
                    }
                }
            }


            List<CommodityVo> list = result;
            return list;
        }
    }

    /**
     * 推荐热门商品
     * @return
     */
    @Override
    public ArrayList<CommodityVo> recommendSome() {
        ArrayList<RecommendNew> num;
        num = userShopMapper.selectCommodityNum();
        //对商品数量进行降序排列
        Collections.sort(num,(o1, o2) -> {
            return o2.getNum() - o1.getNum();
        });

        //找出热门商品并返回
        ArrayList<CommodityVo> commodities = new ArrayList<>();
        for(int i = 0; i < 3; i ++){
            //查找商品信息
            commodities.add(userShopMapper.getById(
                    num.get(i).getCommodityId()
            ));
        }
        return commodities;
    }


    /**
     * 用户订单分页查询
     * @param OrderPageDto
     * @return
     */
    @Override
    public PageResult showShopOrder(OrderPageDto OrderPageDto) {
        PageHelper.startPage(OrderPageDto.getPage(),OrderPageDto.getPageSize());
        Page<UserShopOrderVo> page = userShopMapper.showShopOrder(OrderPageDto);
        Long total = page.getTotal();
        List<UserShopOrderVo> records = page.getResult();
        return new PageResult(total,records);
    }


    /**
     * 提交订单
     * @param list
     */
    @Override
    public void submitOrder(List<OrderCommodityVo> list) {
        for(OrderCommodityVo orderCommodityVo:list){
            if(userShopMapper.findOrderCommodities(orderCommodityVo.getCommodityId()) == null){
                //如果商品未被购买过，则新增商品信息
                userShopMapper.submit(orderCommodityVo);
            }else{
                //商品被购买过，则修改商品信息
                userShopMapper.updateOrderCommodity(orderCommodityVo);
            }

        }
    }

    /**
     * 用户确认收货（超市订单）
     * @param id
     */

    @Override
    public void confirmShopOrder(Integer id) {
        if(userShopMapper.selectOrderById(id) == null){
            throw new ShopOrderNotFound("订单不存在");
        }else{
            ShopOrder shopOrder = new ShopOrder();
            shopOrder.setId(id);
            shopOrder.setStatus(3);
            userShopMapper.updateOrder(shopOrder);

        }
    }


    /**
     * 根据订单id查询订单详细信息
     * @param orderId
     * @return
     */
    @Override
    public ShopOrder findShopOrder(Integer orderId) {
        if(userShopMapper.selectOrderById(orderId) != null) {
            return userShopMapper.selectOrderById(orderId);
        }else{
            throw new ShopOrderNotFound("订单不存在");
        }
    }
}
