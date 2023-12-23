package com.bjpowernode.money.service;

import com.bjpowernode.money.mapper.LoanInfoMapper;
import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.utils.Constants;
import com.bjpowernode.money.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 产品业务实现类
 * author：MaxWell
 * Date：2023/8/14
 */
@Component
@RequestMapping(path = "/LoanInfoService")
public class LoanInfoServiceImpl {
    @Autowired
    LoanInfoMapper loanInfoMapper;

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    //动力金融网历史年化收益率
    @GetMapping("queryLoanInfoHistoryRateAvg")
    @ResponseBody
    public Double queryLoanInfoHistoryRateAvg() {
        //通过工具类常量对应的值，查询缓存获得展示值
        Double loanInfoHistoryRateAvg = (Double)redisTemplate.opsForValue().get(Constants.LOAN_INFO_HISTORY_RATE_AVG);
        //如果存在直接返回
        if(loanInfoHistoryRateAvg!=null){
            return loanInfoHistoryRateAvg;
        }
        if(loanInfoHistoryRateAvg==null) {
            //如果缓存中值不存在，访问数据库得到值
            loanInfoHistoryRateAvg = loanInfoMapper.selectLoanInfoHistoryRateAvg();

            if(loanInfoHistoryRateAvg!=null){
                //数据库中值存在，将从数据库中查询出的loanInfoHistoryRateAvg值存入缓存
                redisTemplate.opsForValue().set(Constants.LOAN_INFO_HISTORY_RATE_AVG, loanInfoHistoryRateAvg, 20, TimeUnit.SECONDS);
            }else{
                //数据库中值不存在，存入缓存里一个假的值
                redisTemplate.opsForValue().set(Constants.LOAN_INFO_HISTORY_RATE_AVG, null , 20, TimeUnit.SECONDS);
            }
        }
        return loanInfoHistoryRateAvg;
    }

    //首页：根据产品类型和数量 查询 产品信息
    @GetMapping("queryLoanInfosByTypeAndNum")
    @ResponseBody
    public List<LoanInfo> queryLoanInfosByTypeAndNum(Integer ptype, Integer start, Integer content) {
        Map<String, Object> parasMap = new HashMap<String, Object>();
        parasMap.put("ptype", ptype);
        parasMap.put("start", start);
        parasMap.put("content", content);
        return loanInfoMapper.selectLoanInfosByTypeAndNum(parasMap);
    }

    // 列表：根据类型和分页模型 查询 数据
    @GetMapping("queryLoanInfosByTypeAndPageModel")
    @ResponseBody
    public List<LoanInfo> queryLoanInfosByTypeAndPageModel(Integer ptype, PageModel pageModel) {

        Map<String,Object> parasMap=new HashMap<>();

        parasMap.put("ptype", ptype);
        //从哪一条开始展示，当前页减去1，再乘以每页展示的条数。
        // 即第一页从第 （1-1）*10=0 条展示，第二页从第（2-1）*10=10条开始展示
        parasMap.put("start", (pageModel.getCunPage()-1)*pageModel.getPageSize());
        //每页展示10条
        parasMap.put("content", pageModel.getPageSize());

        //Long totalCount=1000l;
        //pageModel.setTotalCount(totalCount);
        //return pageModel;
        return loanInfoMapper.selectLoanInfosByTypeAndNum(parasMap);
    }

    //列表：根据产品类型 查询 产品数量
    @GetMapping("queryLoanInfoCountByType")
    @ResponseBody
    public Long queryLoanInfoCountByType(Integer ptype) {

        return  loanInfoMapper.selectLoanInfoCountByType( ptype);
    }

    //详情页面：根据产品编号 查询 产品信息
    @GetMapping("queryLoanInfoByLoanId")
    @ResponseBody
    public LoanInfo queryLoanInfoByLoanId(Integer loanId) {
        return  loanInfoMapper.selectByPrimaryKey(loanId);
    }


}
