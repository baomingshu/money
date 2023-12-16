package com.bjpowernode.money.service;

import com.bjpowernode.money.model.BidInfo;

import java.util.List;

/**
 * 投资业务接口
 */
public interface BidInfoService {

    /**
     * 累计成交总金额
     */
    Double queryBidMoneySum();

    /**
     * 详情页面：根据产品编号 查询 产品信息
     * @param loanId
     * @return
     */
    List<BidInfo> queryBidInfosByLoanId(Integer loanId);
}
