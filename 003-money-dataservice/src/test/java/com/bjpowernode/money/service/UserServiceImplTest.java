package com.bjpowernode.money.service;

import com.bjpowernode.money.mapper.FinanceAccountMapper;
import com.bjpowernode.money.mapper.UserMapper;
import com.bjpowernode.money.utils.Constants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest

public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserMapper userMapper;

    @Mock
    FinanceAccountMapper financeAccountMapper;

    @Mock
    RedisTemplate redisTemplate;
    @Test
    public void testQueryUserCount(){
        ValueOperations value=mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(value);
        when(value.get(Constants.USER_COUNT)).thenReturn(1222l);
        assertEquals(1222l, userService.queryUserCount());

        when(value.get(Constants.USER_COUNT)).thenReturn(null);
        when(userMapper.selectUserCount()).thenReturn(234l);
        doNothing().when(value).set(Constants.USER_COUNT, 234l, 33, TimeUnit.SECONDS);
        assertEquals(234l, userService.queryUserCount());
    }
    @Test
    public void testCheckPhone(){

        when(userMapper.selectUserCountByPhone("phone")).thenReturn(12);
        assertEquals(12, userService.checkPhone("phone"));
    }
    @Test
    public void testRegister(){


    }
}
