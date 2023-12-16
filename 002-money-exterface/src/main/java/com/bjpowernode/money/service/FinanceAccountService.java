package com.bjpowernode.money.service;

import com.bjpowernode.money.model.FinanceAccount;

/**
 * 账户业务接口
 */
public interface FinanceAccountService {
    /**
     * 登陆后：根据用户编号查询账户余额
     * @param userId
     * @return
     */
    FinanceAccount queryFinaneAccountByUserId(Integer userId);
}
