package com.bjpowernode.money.service;

import com.bjpowernode.money.mapper.EvaluateMapper;
import com.bjpowernode.money.mapper.LoanInfoMapper;
import com.bjpowernode.money.model.Evaluate;
import com.bjpowernode.money.model.LoanInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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

    //根据评价eid查询该产品的信息
    @PostMapping("/queryInfoByEid")
    @ResponseBody
    public String queryInfoByEid(Integer eid){

        LoanInfo loanInfo= loanInfoMapper.selectInfoByEid(eid);
      //  return loanInfo;

        // 创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString="";
        try {
            // 将对象转换为JSON字符串
             jsonString = objectMapper.writeValueAsString(loanInfo);

        } catch (JsonProcessingException e) {
            e.printStackTrace();

        }
        return jsonString;
    };
    //插入评论和点赞
    @PostMapping("/insertEvaluate")
    @ResponseBody
    public Integer insertEvaluate(@RequestBody String eval){

        //接收json字符串，获得eid等值，插入数据库
        ObjectMapper objectMapper = new ObjectMapper();
        //创建空对象
        Evaluate eva =null;
        try {
            //解析为转为对象，
            eva = objectMapper.readValue(eval, Evaluate.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

       Integer num= evaluateMapper.insertEval(eva);
        //获得自增主键值
        int i=eva.getEid();
        //插入数据库后调用queryEvalByEid，根据评价eid查询评价是否存在

        //如果存在返回 num
        return num;
    }



}
