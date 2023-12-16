package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.mapper.FinanceAccountMapper;
import com.bjpowernode.money.model.FinanceAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 账户业务实现类
 */
@Service(interfaceClass = FinanceAccountService.class,version = "1.0.0",timeout = 20000)
@Component
public class FinanceAccountServiceImpl implements FinanceAccountService {

    @Autowired
    FinanceAccountMapper financeAccountMapper;

    //登陆后：根据用户编号查询账户余额
    @Override
    public FinanceAccount queryFinaneAccountByUserId(Integer userId) {
        return financeAccountMapper.selectFinaneAccountByUserId( userId);
    }
}
