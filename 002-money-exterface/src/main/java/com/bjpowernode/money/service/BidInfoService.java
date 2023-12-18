package com.bjpowernode.money.service;

import com.bjpowernode.money.model.BidInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 投资业务接口
 */
@FeignClient(name = "BidInfoService", url = "localhost:9003/003-money-dataservice/BidInfoService")
@Component
public interface BidInfoService {

    /**
     * 累计成交总金额
     */
    @GetMapping("/queryBidMoneySum")
    Double queryBidMoneySum();

    /**
     * 详情页面：根据产品编号 查询 产品信息
     * @param loanId
     * @return
     */
    @GetMapping("/queryBidInfosByLoanId")
    List<BidInfo> queryBidInfosByLoanId(Integer loanId);
}
