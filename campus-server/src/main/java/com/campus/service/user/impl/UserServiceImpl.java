package com.campus.service.user.impl;

import com.campus.mapper.AdminUserMapper;
import com.campus.mapper.UserMapper;
import com.campus.service.user.UserService;
import com.campus.dto.RechargeDto;
import com.campus.dto.UserDto;
import com.campus.dto.LoginDto;
import com.campus.entity.User;
import com.campus.exception.AccountExist;
import com.campus.exception.AccountNotFoundException;
import com.campus.exception.AccountStatusException;
import com.campus.exception.PasswordErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminUserMapper adminUserMapper;

    /**
     * 更新用户信息
     * @param userDto
     */
    public void update(UserDto userDto) {
        log.info("开始更新用户信息 service...");
        User user1 = userMapper.selectUSerById(userDto.getId());
        System.out.println(user1);
        if(user1 ==  null) {
            throw new AccountNotFoundException("账号不存在");
        }
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        System.out.println(user);
        userMapper.update(user);
    }

    /**
     * 注销账号
     * @param id
     */
    public void deregister(Integer id) {
        userMapper.deregister(id);
    }

    /**
     * 查看钱包
     * @param id
     * @return
     */
    @Override
    public Double selectWallet(Integer id) {
        return userMapper.selectWallet(id);
    }

    /**
     * 登录
     * @param loginDto
     * @return
     */
    @Override
    public User login(LoginDto loginDto) {

        User user = userMapper.getByUserName(loginDto.getAccount());
        if(user == null){
            throw new AccountNotFoundException("账号不存在");
        }
        //密码验证
        String password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());

        if(!password.equals(user.getPassword())){
            throw new PasswordErrorException("密码错误");
        }

        if(user.getStatus() == 2 ){
            throw new AccountStatusException("账号状态异常");
        }

        return user;
    }

    /**
     * 注册
     * @param userDto
     */
    @Override
    public void register(UserDto userDto) {
        String account = userDto.getAccount();
        if(userMapper.getByUserName(account) == null){
            String password = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
            //如果账号不存在，则继续注册
            User user = new User();
            user.setAccount(userDto.getAccount());
            user.setNickname("nickname");
            user.setPassword(password);
            user.setStatus(1);
            user.setWallet(0);
            user.setRiderWallet(0);
            System.out.println(user);
//            userMapper.submit(user);
            adminUserMapper.save(user);
        }else{
            throw new AccountExist("账号名已被占用");
        }

    }


    /**
     * 根据用户账号查询用户
     * @param account
     * @return
     */
    @Override
    public User selectUserId(String account) {
        if(userMapper.selectUserByAccount(account) == null)
        {
            throw new AccountNotFoundException("注册失败");
        }else{
            return userMapper.selectUserByAccount(account);
        }
    }

    /**
     * 充值
     * @param rechargeDto
     */
    @Override
    public void recharge(RechargeDto rechargeDto) {
        User user = new User();
        user.setId(rechargeDto.getId());
        user.setWallet(rechargeDto.getWallet());
        userMapper.update(user);
    }


    @Override
    public User selectUserById(Integer id) {
        if(userMapper.selectUSerById(id) == null) {
            throw new AccountNotFoundException("用户不存在！");
        }else{
            return userMapper.selectUSerById(id);
        }
    }
}
