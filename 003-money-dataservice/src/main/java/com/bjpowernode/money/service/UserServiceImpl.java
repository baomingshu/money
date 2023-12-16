package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.mapper.FinanceAccountMapper;
import com.bjpowernode.money.mapper.UserMapper;
import com.bjpowernode.money.model.FinanceAccount;
import com.bjpowernode.money.model.User;
import com.bjpowernode.money.utils.Constants;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 用户业务实现类
 */
@Service(interfaceClass = UserService.class,version = "1.0.0",timeout = 20000)
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    FinanceAccountMapper financeAccountMapper;

    @Autowired(required = false)
    RedisTemplate redisTemplate;


    //平台用户数：总人数
    @Override
    public Long queryUserCount() {
        //通过工具类常量对应的值，获得展示值
        Long userCount = (Long)redisTemplate.opsForValue().get(Constants.USER_COUNT);
        //课后：处理好缓存穿透现象！
        if(userCount==null){
            //如果缓存中值不存在，访问数据库得到值
            userCount=userMapper.selectUserCount();
            //设置从数据库获得的值，有效时间33秒
            redisTemplate.opsForValue().set(Constants.USER_COUNT, userCount, 33, TimeUnit.SECONDS);
        }else{
            //如果是从缓存照中查到的数据打印 --缓存命中--
            System.out.println("---缓存命中---");
        }


        return userCount;
    }

    //注册：根据手机号码查询用户数量
    @Override
    public int checkPhone(String phone) {
        return  userMapper.selectUserCountByPhone(phone);
    }

    //注册：注册
    @Override
    public User register(String phone, String loginPassword) {

        User user=new User();
        user.setAddTime(new Date());
        user.setLoginPassword(loginPassword);
        user.setPhone(phone);
        int num= userMapper.insertSelective(user);
        if(num==1){

            //课后：维护一个线程池
            new Thread(new Runnable(){

                @Override
                public void run() {
                    //送大礼
                    FinanceAccount financeAccount=new FinanceAccount();
                    financeAccount.setAvailableMoney(888d);
                   // Connection connection;
                   //connection.prepareStatement("", Statement.RETURN_GENERATED_KEYS);
                    financeAccount.setUid(user.getId());
                    financeAccountMapper.insertSelective(financeAccount);
                }
            }).start();

            return user;
        }
        return null;
    }

    //登录：登录
    @Override
    public User login(String phone, String loginPassword) {
       User user= userMapper.selectUserByPhoneAndPasswd(phone,loginPassword);
       if(ObjectUtils.allNotNull(user)){
           new Thread(new Runnable(){
               @Override
               public void run() {
                   user.setLastLoginTime(new Date());
                   userMapper.updateByPrimaryKeySelective(user);
               }
           }).start();
       }

       return user;
    }
}
