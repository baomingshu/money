package com.bjpowernode.money.service;

import com.bjpowernode.money.mapper.BidInfoMapper;
import com.bjpowernode.money.utils.Constants;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.security.RunAs;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tesr for {@link BidInfoServiceImpl}
 * @author bms
 * @since 1.0
 */
//spring-boot-starter-test默认集成了junit5依赖，Mockito: Java Mock框架依赖，AssertJ流式断言等
// 在junit4的时候使用@Runwith,在junit5的时候使用@SpringBootTest，作用是加载web Application Context并提供Mock Web Environment
@SpringBootTest
public class BidInfoServiceImplTest {
    //把要测试的类通过mock注入
    @InjectMocks
    BidInfoServiceImpl bidInfoServiceImpl;
    //在运行时，需要用到的对象，使用@mock造假一个对象
    @Mock
    RedisTemplate redisTemplate;
    //在运行时，需要用到的对象，使用@mock造假一个对象
    @Mock
    BidInfoMapper bidInfoMapper;


    @Test
    public void testQueryBidMoneySum(){
        //不为空的情况
        //造一个假的值
        ValueOperations value = mock(ValueOperations.class);
        //redisTemplate调用opsForValue，获得（返回）这个mock值
        when(redisTemplate.opsForValue()).thenReturn(value);
        //获得这个mock值value后，调用get方法。得到返回值是5.345
        when(value.get(Constants.BID_MONEY_SUM)).thenReturn(5.345);
        //本类的对象调用需要测试的方法queryBidMoneySum，期望返回值是5.345
        assertEquals(5.345, bidInfoServiceImpl.queryBidMoneySum());

        //缓存为空的情况
        when(redisTemplate.opsForValue()).thenReturn(value);
        //获得这个mock值value后，调用get方法。得到返回值是null
        when(value.get(Constants.BID_MONEY_SUM)).thenReturn(null);
        //当返回值是null，执行selectBidMoneySum去数据库查询，得到返回值是8.88
        when(bidInfoMapper.selectBidMoneySum()).thenReturn(8.88);
        //将从数据库获得的值设置到缓存，有效时间42秒。 因为没有返回值所以使用doNothing().when(value)
        doNothing().when(value).set(Constants.BID_MONEY_SUM, 8.88, 42, TimeUnit.SECONDS);
        //本类的对象调用需要测试的方法queryBidMoneySum，期望返回值是8.88
        assertEquals(8.88, bidInfoServiceImpl.queryBidMoneySum());
    }
    @Test
    public void testQueryBidInfosByLoanId(){
        List list = mock(List.class);
        when(bidInfoMapper.selectBidInfosByLoanId(5)).thenReturn(list);
        assertEquals( list,bidInfoServiceImpl.queryBidInfosByLoanId(5));
    }
    @Test
    public void testFact(){

        assertEquals(2,bidInfoServiceImpl.fact(1));
        assertEquals(4,bidInfoServiceImpl.fact(3));
    }
}
