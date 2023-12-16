package com.bjpowernode.money.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.money.mapper.LoanInfoMapper;
import com.bjpowernode.money.model.LoanInfo;
import com.bjpowernode.money.utils.Constants;
import com.bjpowernode.money.utils.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 产品业务实现类
 * author：MaxWell
 * Date：2023/8/14
 */
@Service(interfaceClass = LoanInfoService.class,version = "1.0.0",timeout = 20000)
@Component
public class LoanInfoServiceImpl implements LoanInfoService {
    @Autowired
    LoanInfoMapper loanInfoMapper;

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    //动力金融网历史年化收益率
    @Override
    public Double queryLoanInfoHistoryRateAvg() {
        //通过工具类常量对应的值，获得展示值
        Double loanInfoHistoryRateAvg = (Double)redisTemplate.opsForValue().get(Constants.LOAN_INFO_HISTORY_RATE_AVG);
        //课后：处理好缓存穿透现象！，但是我忘了，而且没空写啊，好难啊
        if(loanInfoHistoryRateAvg==null){
            //如果缓存中值不存在，访问数据库得到值
            loanInfoHistoryRateAvg=loanInfoMapper.selectLoanInfoHistoryRateAvg();
            //设置从数据库获得的值，有效时间20秒
            redisTemplate.opsForValue().set(Constants.LOAN_INFO_HISTORY_RATE_AVG, loanInfoHistoryRateAvg, 20, TimeUnit.SECONDS);
        }else{
            //如果是从缓存照中查到的数据打印 --缓存命中--
            System.out.println("---缓存命中---");
        }
        return loanInfoHistoryRateAvg;
    }

    //首页：根据产品类型和数量 查询 产品信息
    @Override
    public List<LoanInfo> queryLoanInfosByTypeAndNum(Map<String, Object> parasMap) {

        return loanInfoMapper.selectLoanInfosByTypeAndNum(parasMap);
    }

    // 列表：根据类型和分页模型 查询 数据
    @Override
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
    @Override
    public Long queryLoanInfoCountByType(Integer ptype) {

        return  loanInfoMapper.selectLoanInfoCountByType( ptype);
    }

    //详情页面：根据产品编号 查询 产品信息
    @Override
    public LoanInfo queryLoanInfoByLoanId(Integer loanId) {
        return  loanInfoMapper.selectByPrimaryKey(loanId);

    }
}
