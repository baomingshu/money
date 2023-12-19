package com.bjpowernode.money.service;

import com.bjpowernode.money.mapper.FinanceAccountMapper;
import com.bjpowernode.money.model.FinanceAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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
