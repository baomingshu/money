package com.bjpowernode.money.service;

import com.bjpowernode.money.mapper.BidInfoMapper;
import com.bjpowernode.money.model.BidInfo;
import com.bjpowernode.money.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 投资业务实现类
 */
//服务暴露
@Component
@RequestMapping(path = "/BidInfoService")
public class BidInfoServiceImpl implements BidInfoService {

    @Autowired
    BidInfoMapper bidInfoMapper;

    @Autowired(required = false)
    RedisTemplate redisTemplate;


    // 累计成交额：总金额
    @Override
    @GetMapping("/queryBidMoneySum")
    @ResponseBody
    public Double queryBidMoneySum() {
        //通过工具类常量对应的值，获得展示值
        Double bidMoneySum = (Double) redisTemplate.opsForValue().get(Constants.BID_MONEY_SUM);
        if (bidMoneySum != null) {
            return bidMoneySum;
        }
        if (bidMoneySum == null) {
            //如果缓存中值不存在，访问数据库得到值
            bidMoneySum = bidInfoMapper.selectBidMoneySum();
            if (bidMoneySum != null) {
                //将从数据库获得的值设置到缓存，有效时间42秒
                redisTemplate.opsForValue().set(Constants.BID_MONEY_SUM, bidMoneySum, 42, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(Constants.BID_MONEY_SUM, null, 42, TimeUnit.SECONDS);
            }
        }
        return bidMoneySum;
    }

    // 详情页面：根据产品编号 查询 产品信息
    @Override
    @ResponseBody
    public List<BidInfo> queryBidInfosByLoanId(Integer loanId) {

        return bidInfoMapper.selectBidInfosByLoanId(loanId);

    }

    public int fact(int i) {
        int a = ++i;
        return a;
    }
}
