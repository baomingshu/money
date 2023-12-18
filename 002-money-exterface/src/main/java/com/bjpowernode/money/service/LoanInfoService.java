package com.bjpowernode.money.service;

import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.utils.PageModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Map;

/**
 *产品业务接口
 * author：MaxWell
 * Date：2023/8/14
 */
@Component
@FeignClient(name = "LoanInfoService", url = "localhost:9003/003-money-dataservice/LoanInfoService")
public interface LoanInfoService {

    /**
     * 动力金融网历史年化收益率
     * @return :历史年化收益率
     */
    @GetMapping("queryLoanInfoHistoryRateAvg")
    Double queryLoanInfoHistoryRateAvg();


    /**
     * 首页：根据产品类型和数量 查询 产品信息
     * @param ptype：类型和数量
     * @param start：类型和数量
     * @param content：类型和数量
     * @return 产品集合
     */
    @GetMapping("queryLoanInfosByTypeAndNum")
    List<LoanInfo> queryLoanInfosByTypeAndNum(@RequestParam("ptype") Integer ptype, @RequestParam("start")Integer start,@RequestParam("content") Integer content );

    /**
     * 列表：根据类型和分页模型 查询 数据
     * @param ptype
     * @param pageModel
     * @return
     */
    @GetMapping("queryLoanInfosByTypeAndPageModel")
    List<LoanInfo> queryLoanInfosByTypeAndPageModel(@RequestParam("ptype")Integer ptype, @RequestParam("pageModel")PageModel pageModel);

    /**
     * 列表：根据产品类型 查询 产品数量
     * @param ptype
     * @return
     */
    @GetMapping("queryLoanInfoCountByType")
    Long queryLoanInfoCountByType(@RequestParam Integer ptype);

    /**
     * 详情页面：根据产品编号 查询 产品信息
     * @param loanId
     * @return
     */
    @GetMapping("queryLoanInfoByLoanId")
    LoanInfo queryLoanInfoByLoanId(Integer loanId);


}
