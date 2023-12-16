package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.LoanInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LoanInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoanInfo record);

    int insertSelective(LoanInfo record);

    LoanInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanInfo record);

    int updateByPrimaryKey(LoanInfo record);

    //动力金融网历史年化收益率
    Double selectLoanInfoHistoryRateAvg();

    //首页：根据产品类型和数量 查询 产品信息
    List<LoanInfo> selectLoanInfosByTypeAndNum(Map<String, Object> parasMap);

    //列表：根据产品类型 查询 产品数量
    Long selectLoanInfoCountByType(Integer ptype);
}