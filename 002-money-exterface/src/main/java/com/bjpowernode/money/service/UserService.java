package com.bjpowernode.money.service;

import com.bjpowernode.money.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户业务接口
 */
@Component
@FeignClient(name = "UserService", url = "localhost:9003/003-money-dataservice/UserService")
public interface UserService {

    /**
     *  //平台用户数：总人数
     * @return
     */
    @GetMapping("queryUserCount")
    Long queryUserCount();

    /**
     * 注册：根据手机号码查询用户数量
     * @param phone
     * @return
     */
    @GetMapping("checkPhone")
    int checkPhone(String phone);

    /**
     * 注册：注册
     * @param phone
     * @param loginPassword
     * @return
     */
    @GetMapping("register")
    User register(@RequestParam("phone")String phone, @RequestParam("loginPassword")String loginPassword);

    /**
     * 登录：登录
     * @param phone
     * @param loginPassword
     * @return
     */
    @GetMapping("login")
    User login(@RequestParam("phone")String phone, @RequestParam("loginPassword")String loginPassword);

}
