package com.bjpowernode.money.service;

import com.bjpowernode.money.mapper.LoanInfoMapper;
import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.utils.Constants;
import com.bjpowernode.money.utils.PageModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoanInfoServiceImplTest {
    //把要测试的类通过mock注入
    @InjectMocks
    LoanInfoServiceImpl loanInfoServiceImpl;
    //在运行时，需要用到的对象，使用@mock造假一个对象
    @Mock
    RedisTemplate redisTemplate;
    //在运行时，需要用到的对象，使用@mock造假一个对象
    @Mock
    LoanInfoMapper loanInfoMapper;
    @Test
    public void testQueryLoanInfoHistoryRateAvg() {
        //不为空
        ValueOperations value=mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(value);
        when(value.get(Constants.LOAN_INFO_HISTORY_RATE_AVG)).thenReturn(1.11);
        assertEquals(1.11, loanInfoServiceImpl.queryLoanInfoHistoryRateAvg());

        //为空
        when(value.get(Constants.LOAN_INFO_HISTORY_RATE_AVG)).thenReturn(null);
        when(loanInfoMapper.selectLoanInfoHistoryRateAvg()).thenReturn(2.22);
        doNothing().when(value).set(Constants.LOAN_INFO_HISTORY_RATE_AVG, 2.22, 20, TimeUnit.SECONDS);
        assertEquals(2.22, loanInfoServiceImpl.queryLoanInfoHistoryRateAvg());
    }
    @Test
    public void testQueryLoanInfosByTypeAndNum(){
        List list = mock(List.class);
        when(loanInfoMapper.selectLoanInfosByTypeAndNum(any())).thenReturn(list);
        assertEquals(list, loanInfoServiceImpl.queryLoanInfosByTypeAndNum(1, 2, 3));
    }
    @Test
    public void testQueryLoanInfosByTypeAndPageModel(){

        List list=mock(List.class);
        PageModel model =mock(PageModel.class);
        when(loanInfoMapper.selectLoanInfosByTypeAndNum(any())).thenReturn(list);
        assertEquals(list, loanInfoServiceImpl.queryLoanInfosByTypeAndPageModel(1, model));

    }
    @Test
    public void testQueryLoanInfoCountByType() {
        when(loanInfoMapper.selectLoanInfoCountByType(4)).thenReturn(123451235l);
        assertEquals(123451235l, loanInfoServiceImpl.queryLoanInfoCountByType(4));

    }
    @Test
    public void testQueryLoanInfoByLoanId(){
        LoanInfo loanInfo=mock(LoanInfo.class);
        when(loanInfoMapper.selectByPrimaryKey(4)).thenReturn(loanInfo);
        assertEquals(loanInfo,loanInfoServiceImpl.queryLoanInfoByLoanId(4));
    }
}
