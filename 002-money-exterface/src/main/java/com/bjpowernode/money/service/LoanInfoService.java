package com.bjpowernode.money.service;

import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.utils.PageModel;


import java.util.List;
import java.util.Map;

/**
 *产品业务接口
 * author：MaxWell
 * Date：2023/8/14
 */
public interface LoanInfoService {

    /**
     * 动力金融网历史年化收益率
     * @return :历史年化收益率
     */
    Double queryLoanInfoHistoryRateAvg();


    /**
     * 首页：根据产品类型和数量 查询 产品信息
     * @param parasMap：类型和数量
     * @return 产品集合
     */
    List<LoanInfo> queryLoanInfosByTypeAndNum(Map<String, Object> parasMap);

    /**
     * 列表：根据类型和分页模型 查询 数据
     * @param ptype
     * @param pageModel
     * @return
     */
    List<LoanInfo> queryLoanInfosByTypeAndPageModel(Integer ptype, PageModel pageModel);

    /**
     * 列表：根据产品类型 查询 产品数量
     * @param ptype
     * @return
     */
    Long queryLoanInfoCountByType(Integer ptype);

    /**
     * 详情页面：根据产品编号 查询 产品信息
     * @param loanId
     * @return
     */
    LoanInfo queryLoanInfoByLoanId(Integer loanId);


}
