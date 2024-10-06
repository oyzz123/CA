package com.campus.service.user.impl;

import com.campus.mapper.UserMapper;
import com.campus.mapper.UserVoucherMapper;
import com.campus.service.user.UserVoucherService;
import com.constant.VoucherKillConstant;
import com.entity.User;
import com.entity.Voucher;
import com.entity.VoucherOrder;
import com.exception.VoucherException;
import com.exception.VoucherKillException;
import com.exception.WalletException;
import com.utils.RedisIdUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserVoucherServiceImpl implements UserVoucherService {
    @Autowired
    private UserVoucherMapper userVoucherMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 购买普通优惠券
     */
    @Override
    public void getVoucher(Integer userId, Integer id) {
        Voucher voucher = userVoucherMapper.selectVoucherById(id);
        if(voucher == null) {
            throw new VoucherException("优惠券不存在");
        }
        if(voucher.getStatus() != 1) {
            throw new VoucherException("优惠券状态错误");
        }
        if(voucher.getNum() < 0) {
            throw new VoucherException("优惠券已售空");
        }
        if(voucher.getType() != 1) {
            throw new VoucherException("优惠券类型错误");
        }
        //查询用户余额
        Double wallet = userMapper.selectWallet(userId);
        if(wallet < voucher.getPayMoney()) {
            //钱包余额不足以支付优惠券
            throw new WalletException("余额不足");
        }
        synchronized (this){
            //支付
            User user = new User();
            user.setId(userId);
            user.setWallet(voucher.getPayMoney());
            userMapper.payVoucher(user);

            //生成优惠券订单对象
            createVoucherOrder(userId, voucher);

            //扣减库存
            userVoucherMapper.reduceVoucherNum(voucher.getId());
        }
    }


    /**
     * 优惠券秒杀
     */
    @Override
    public void killVoucher(Integer userId, Integer id) throws InterruptedException {
        Voucher voucher = userVoucherMapper.selectVoucherById(id);
        if(voucher == null) {
            throw new VoucherException("优惠券不存在");
        }
        if(voucher.getStatus() != 1) {
            throw new VoucherException("优惠券状态错误");
        }
        if(LocalDateTime.now().isBefore(voucher.getStartTime())) {
            //秒杀尚未开始
            throw new VoucherKillException("秒杀尚未开始");
        }
        if(LocalDateTime.now().isAfter(voucher.getEndTime())) {
            //秒杀已经结束
            throw new VoucherKillException("秒杀已经结束");
        }
        if(userMapper.selectWallet(userId) < voucher.getPayMoney()) {
            throw new WalletException("余额不足");
        }
        if(voucher.getNum() < 0) {
            throw new VoucherException("优惠券已售空");
        }
        if(voucher.getType() != 2) {
            throw new VoucherException("优惠券类型错误");
        }

        //扣减账户余额 扣减优惠券库存
        Boolean res = killVoucherService(userId, voucher);

        if(!res) {
            throw new VoucherKillException(VoucherKillConstant.VOUCHER_HAS_CHANGE);
        }
        //生成优惠券订单
        createVoucherOrder(userId, voucher);

    }

    private void createVoucherOrder(Integer userId, Voucher voucher) {
        VoucherOrder voucherOrder = new VoucherOrder();
        RedisIdUtil redisIdUtil = new RedisIdUtil(stringRedisTemplate);
        voucherOrder.setId(redisIdUtil.nextId("voucher"));
        voucherOrder.setVoucherId(voucher.getId());
        voucherOrder.setUserId(userId);
        voucherOrder.setPayTime(LocalDateTime.now());

        userVoucherMapper.saveVoucherOrder(voucherOrder);
    }

    @Transactional
    public Boolean killVoucherService(Integer userId, Voucher voucher) throws InterruptedException {
        int voucherId = voucher.getId();
        String lockName = "lock:userId:";
        RLock lock = redissonClient.getLock(lockName + voucherId);
        boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);
        if(isLock) {
            try {
                Integer num = voucher.getNum();

                //扣减账户余额
                User user = new User();
                user.setId(userId);
                user.setWallet(voucher.getPayMoney());
                userMapper.pay(user);
                //扣减库存
                if(userVoucherMapper.selectVoucherById(voucher.getId()).getNum() == num) {
                    //一人一单，库存扣减一
                    userVoucherMapper.reduceVoucherNum(voucher.getId());
                }else{
                    throw new VoucherKillException(VoucherKillConstant.VOUCHER_HAS_CHANGE);
                }
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;
    }
}
