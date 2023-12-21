package com.bjpowernode.money.web;


import com.bjpowernode.money.model.User;
import com.bjpowernode.money.service.LoginService;
import com.bjpowernode.money.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.Map;

//使用@Controller 注解，在对应的方法上，视图解析器可以解析return 的jsp,html页面，并且跳转到相应页面
//若返回json等内容到页面，则需要加@ResponseBody注解,
//@RestController注解，相当于@Controller+@ResponseBody结合,但使用@RestController这个注解，就不能返回jsp,html页面
@Controller
//@RequestMapping 默认method是get,post方式都支持
@RequestMapping("/loan/page")
public class LoginController {
    @Autowired
    LoginService loginService;

    //get请求，参数不能是json格式，只能是表单格式
    @GetMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    //post请求，如果是接收json格式,接收参数要是一个参数或者是一个对象并且参数前加上@RequestBody注解
    @PostMapping(value = "/registerSubmit")
    @ResponseBody
    public Map<String,Object> register(@RequestBody User user, HttpServletRequest request) {
        Result result = new Result();
        String phone=user.getPhone();
        String pwd=user.getLoginPassword();
        //查询用户是否存在，
        Integer num = loginService.queryRegister(phone);
        if (num > 0) {
            // 如果数据库存在用户信息，返回失败信息
            return Result.error("用户已存在，前往登录页面");
        } else {
            //如果不存在，将用户信息存入数据库
            loginService.registerByPhoneAndPwd(phone, pwd);
            //再次查询是否存入成功
            Integer Num2 = loginService.queryRegister(phone);
            //存入成功向前端发送信息
            return Result.success("注册成功，请前往登录页面");
        }

    }

    @GetMapping("/login")
    public Object login(@RequestParam(name = "phone", required = true) String phone,
                        @RequestParam(name = "pwd") String pwd, HttpServletRequest request, Model model) {
        //根据参数phone查询数据库，selectUserCountByPhone方法//如果查询不到信息，返回账号不存在，
        Integer num =loginService.queryRegister(phone);
        if(num ==0){
            return Result.error("用户不存在，重新登录");
        } else if(num>0){
            //如果phone能查到数据，再根据参数phone和pwd查询数据库
            User user = loginService.loginByaccountAndPwd(phone, pwd);
            if (user==null){
                //查询失败，返回密码错误
                return Result.error("密码 错误，重新登录");
            }else{
                //如果查询到信息，登录成功
                return Result.success("登录成功");
                //将登录信息存到redis，后续可以前往redis查询。
                //登录成功后，还应该做什么？
            }

        }
        return "index";
    }

}
