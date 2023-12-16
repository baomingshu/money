package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.FinanceAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceAccount record);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int updateByPrimaryKey(FinanceAccount record);

    //登陆后：根据用户编号查询账户余额
    FinanceAccount selectFinaneAccountByUserId(Integer userId);
}