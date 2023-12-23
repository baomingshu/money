package com.bjpowernode.money.web;

import com.bjpowernode.money.model.Evaluate;
import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EvaluateController {

    //注入
    @Autowired
    EvaluateService evaluateService;

    @GetMapping("/evaluate")
    public String evaluate(Model model, Integer pid, Integer eid){
        //总星数
        int starsById = evaluateService.queryStarsById(pid);
        model.addAttribute("STAR_BY_ID",starsById);
        //平均星数
        double starsAvgById = evaluateService.queryStarsAvgById(pid);
        model.addAttribute("STAR_AVG_BY_ID",starsAvgById);

        //根据产品id查询该产品的所有评论
        List<Evaluate> list = evaluateService.queryEvaluateById(pid);
        model.addAttribute("EVA_LIST",list);
        //根据评价id查询该产品的信息
        LoanInfo loanInfo = evaluateService.queryInfoByEid(eid);
        model.addAttribute("LOAN_INFO_EVA",loanInfo);

        return "evaluate";
    }

}
