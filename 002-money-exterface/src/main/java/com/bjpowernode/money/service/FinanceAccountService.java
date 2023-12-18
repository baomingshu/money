package com.bjpowernode.money.service;

import com.bjpowernode.money.model.FinanceAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 账户业务接口
 */
@Component
@FeignClient(name = "FinanceAccountService", url = "localhost:9003/003-money-dataservice/BidInfoService")
public interface FinanceAccountService {
    /**
     * 登陆后：根据用户编号查询账户余额
     * @param userId
     * @return
     */
    FinanceAccount queryFinaneAccountByUserId(Integer userId);
}
