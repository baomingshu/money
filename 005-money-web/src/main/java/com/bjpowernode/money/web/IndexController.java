package com.bjpowernode.money.web;

//import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.service.BidInfoService;
import com.bjpowernode.money.service.LoanInfoService;
import com.bjpowernode.money.service.UserService;
import com.bjpowernode.money.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    LoanInfoService loanInfoService;

    @Autowired
    UserService userService;

    @Autowired
    BidInfoService bidInfoService;

    @GetMapping("/index")
    public String index(Model model){
        //动力金融网历史年化收益率:平均利率
        Double loanInfoHistoryRateAvg= loanInfoService.queryLoanInfoHistoryRateAvg();
        // model.addAttribute("loanInfoHistoryRateAvg", loanInfoHistoryRateAvg);
        model.addAttribute(Constants.LOAN_INFO_HISTORY_RATE_AVG, loanInfoHistoryRateAvg);

        //平台用户数：总人数
        Long userCount= userService.queryUserCount();
        model.addAttribute(Constants.USER_COUNT, userCount);

        //累计成交额：总金额
        Double bidMoneySum=bidInfoService.queryBidMoneySum();
        model.addAttribute(Constants.BID_MONEY_SUM, bidMoneySum);

        //根据产品类型和数量 查询 产品信息
        List<LoanInfo> loanInfoList_X= loanInfoService.queryLoanInfosByTypeAndNum(0, 0, 1);
        model.addAttribute("loanInfoList_X", loanInfoList_X);

        //优选标
        List<LoanInfo> loanInfoList_Y= loanInfoService.queryLoanInfosByTypeAndNum(1, 0, 4);
        model.addAttribute("loanInfoList_Y", loanInfoList_Y);

        //散标
        List<LoanInfo> loanInfoList_S= loanInfoService.queryLoanInfosByTypeAndNum(2,0,8);
        model.addAttribute("loanInfoList_S", loanInfoList_S);


        return "index";
    }

}
