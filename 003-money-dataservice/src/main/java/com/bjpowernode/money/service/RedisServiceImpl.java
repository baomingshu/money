package com.bjpowernode.money.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis 业务实现类
 */
@Component
public class RedisServiceImpl implements RedisService {

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    //注册：存放验证码
    @Override
    public void push(String phone, String code) {
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
    }

    // 注册：获取验证码
    @Override
    public String pop(String phone) {
       return (String) redisTemplate.opsForValue().get(phone);

    }
}
