package com.bjpowernode.money.service;

import com.bjpowernode.money.mapper.EvaluateMapper;
import com.bjpowernode.money.mapper.LoanInfoMapper;
import com.bjpowernode.money.model.Evaluate;
import com.bjpowernode.money.model.LoanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.RegEx;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Component
@RequestMapping (path = "/EvaluateService")
public class EvaluateServiceImpl {

    @Autowired
    EvaluateMapper evaluateMapper;

    @Autowired
    LoanInfoMapper loanInfoMapper;
    //根据产品id查询对应产品总点赞数量
    @GetMapping("queryStarsById")
    @ResponseBody
    public int queryStarsById(Integer pid){
        int sumStar = evaluateMapper.selectStarsById(pid);
        return sumStar;
    };

    //根据产品id查询平均点赞
    @GetMapping("/queryStarsAvgById")
    @ResponseBody
    public double queryStarsAvgById(Integer pid){
        double avgStar =evaluateMapper.selectStarsAvgById(pid);
        return avgStar;
    };

    //根据产品id查询该产品的所有评论
    @GetMapping("/queryEvaluateById")
    @ResponseBody
    public List<Evaluate> queryEvaluateById(Integer pid){

        List<Evaluate> list = evaluateMapper.selectEvaluateByid(pid);
        return list;
    };

    //根据评价id查询该产品的信息
    @GetMapping("/queryInfoByEid")
    @ResponseBody
    public LoanInfo queryInfoByEid(Integer eid){
        LoanInfo loanInfo= loanInfoMapper.selectInfoByEid(eid);
        return loanInfo;
    };
}
