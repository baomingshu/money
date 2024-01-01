package com.bjpowernode.money.service;

import com.bjpowernode.money.model.BidInfo;
import com.bjpowernode.money.model.Evaluate;
import com.bjpowernode.money.model.LoanInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "EvaluateService", url = "localhost:9003/003-money-dataservice/EvaluateService")
@Component
public interface EvaluateService {
    //根据产品id查询对应产品总点赞数量
    @GetMapping("/queryStarsById")
    int queryStarsById(@RequestParam Integer pid);

    //根据产品id查询平均点赞
    @GetMapping("/queryStarsAvgById")
    double queryStarsAvgById(@RequestParam Integer pid);

    //根据产品id查询该产品的所有评论
    @GetMapping("/queryEvaluateById")
    List<Evaluate> queryEvaluateById(@RequestParam Integer pid);

    //根据评价id查询该产品的信息
    @GetMapping("/queryInfoByEid")
    LoanInfo queryInfoByEid(@RequestParam Integer eid);

    //插入评论和点赞
    @GetMapping("/insertEvaluate")
    Integer insertEvaluate(@RequestParam String eval);

    //根据评价eid查询评价是否存在
    @GetMapping("/queryEvalByEid")
    Integer queryEvalByEid(Integer eid);
}
