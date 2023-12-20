package com.bjpowernode.money.service;

import com.bjpowernode.money.mapper.UserMapper;
import com.bjpowernode.money.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RequestMapping(path = "/LoginService")
public class LoginServiceImpl {
    @Autowired
    UserMapper userMapper;

    //注册查询：根据手机号查询用户是否存在，不存在则存入手机号和密码
    @GetMapping("queryRegister")
    @ResponseBody
    public Integer queryRegister( String phone ){
        //根据手机号查询用户是否存在
        Integer Num=userMapper.selectUserCountByPhone(phone);
        //返回Num
            return Num;

    }
    //注册存入数据
    @GetMapping("registerByPhoneAndPwd")
    @ResponseBody
    public int registerByPhoneAndPwd( String phone,String pwd){

        //将注册信息存入user对象，
        User user =new User();
        user.setPhone(phone);
        user.setLoginPassword(pwd);
        //user对象存入数据库
        int num  = userMapper.insert(user);
        return num;
    }

   //登录查询
    @GetMapping("loginByaccountAndPwd")
    @ResponseBody
    public User loginByaccountAndPwd( String phone, String pwd){
        //根据传入的phone和pwd查询数据库
        User user= userMapper.selectUserByPhoneAndPasswd(phone,pwd);

        return user;
    }
}
