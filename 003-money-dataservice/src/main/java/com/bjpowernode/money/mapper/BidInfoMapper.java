package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.BidInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);

    // 累计成交额：总金额
    Double selectBidMoneySum();

    // 详情页面：根据产品编号 查询 产品信息
    List<BidInfo> selectBidInfosByLoanId(Integer loanId);
}