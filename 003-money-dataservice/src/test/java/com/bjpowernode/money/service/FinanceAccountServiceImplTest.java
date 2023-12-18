package com.bjpowernode.money.service;

import com.bjpowernode.money.mapper.FinanceAccountMapper;
import com.bjpowernode.money.model.FinanceAccount;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.meta.When;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FinanceAccountServiceImplTest {
    @InjectMocks
    FinanceAccountServiceImpl financeAccountService;

    @Mock
    FinanceAccountMapper financeAccountMapper;

    @Test
    public void testQueryFinaneAccountByUserId(){
        FinanceAccount financeAccount = mock(FinanceAccount.class);
        when(financeAccountMapper.selectFinaneAccountByUserId(5)).thenReturn(financeAccount);
    }

}
