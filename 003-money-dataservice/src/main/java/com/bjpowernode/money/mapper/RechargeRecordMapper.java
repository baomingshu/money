package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.RechargeRecord;

public interface RechargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);
}