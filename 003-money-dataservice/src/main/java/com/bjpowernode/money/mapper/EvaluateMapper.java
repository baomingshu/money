package com.bjpowernode.money.mapper;

import com.bjpowernode.money.model.Evaluate;
import com.bjpowernode.money.model.LoanInfo;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluateMapper {
    //根据产品id查询对应产品总点赞数量
    int selectStarsById(Integer pid);

    //根据产品id查询平均点赞
    double selectStarsAvgById(Integer pid);

    //根据产品id查询该产品的所有评论
    List<Evaluate> selectEvaluateByid(Integer pid);

}
