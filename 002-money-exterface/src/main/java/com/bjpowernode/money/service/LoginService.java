package com.bjpowernode.money.service;

import com.bjpowernode.money.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Component
@FeignClient(name = "LoginService", url = "localhost:9003/003-money-dataservice/LoginService")
public interface LoginService {

    /*
    * 注册功能
    * @return: Integer
    * */
    @GetMapping("/queryRegister")
    Integer queryRegister(@RequestParam String phone );

    /*
     * 注册功能
     * @return: user
     * */
    @GetMapping("/registerByPhoneAndPwd")
    void registerByPhoneAndPwd(@RequestParam String phone , @RequestParam String pwd);

    /*
     * 登录功能
     * @return: user
     * */
    @GetMapping("/loginByaccountAndPwd")
    User loginByaccountAndPwd(@RequestParam String account,@RequestParam String pwd);
}
