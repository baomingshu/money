package com.bjpowernode.money.web;

//import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.money.model.BidInfo;
import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.service.BidInfoService;
import com.bjpowernode.money.service.LoanInfoService;
import com.bjpowernode.money.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

public class LoanController {
    @Autowired
    LoanInfoService loanInfoService;

    @Autowired
    BidInfoService bidInfoService;

    //课后：大杂烩，，多写几个其他数据
    @GetMapping("/loan/loan")
    public String loan(@RequestParam(name = "ptype",required = true)Integer ptype,Long cunPage,
                       HttpServletRequest request, Model model){


        PageModel pageModel=(PageModel) request.getSession().getAttribute("pageModel");
        if(pageModel==null){
            pageModel=new PageModel(9);
            request.getSession().setAttribute("pageModel", pageModel);
        }

        //课后：在服务器启动的时候，缓存中存放部分产品信息，超出分页边界，从缓存中随机抽取若干信息，用于展现！

        if(cunPage==null||cunPage<pageModel.getFirstPage()){
            //Integer.longValue() 方法将此 Integer 的值转为long类型返回
            cunPage=pageModel.getFirstPage().longValue();
        }

        //查询总记录数
        Long totalCount=loanInfoService.queryLoanInfoCountByType(ptype);
        pageModel.setTotalCount(totalCount);

        if(cunPage>pageModel.getTotalPage()){
            cunPage=pageModel.getTotalPage();
        }
        //设置当前页
        pageModel.setCunPage(cunPage);



        //List<LoanInfo> loanInfoList = loanInfoService.queryLoanInfosByTypeAndNum(parasMap);
        List<LoanInfo> loanInfoList = loanInfoService.queryLoanInfosByTypeAndPageModel(ptype,pageModel);
        model.addAttribute("loanInfoList", loanInfoList);
        model.addAttribute("ptype", ptype);

        //todo：投资排行榜

        return "loan";
    }

    //课后：doFirst()  doUp()  doDonw(){cunPage++}  doLast()  doGo()  ==>封装代码 通用任何地方


    @GetMapping("/loan/loanInfo")
    public String loanInfo(@RequestParam(name="loanId",required = true)Integer loanId,
                           Model model){
        //展现信息
        LoanInfo loanInfo=loanInfoService.queryLoanInfoByLoanId(loanId);
        model.addAttribute("loanInfo", loanInfo);

        //投资记录
        List<BidInfo> bidInfoList=bidInfoService.queryBidInfosByLoanId(loanId);
        model.addAttribute("bidInfoList", bidInfoList);


        return "loanInfo.html";
    }
}
