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
            return Result.success("注册成功，前往登录页面");
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "phone", required = true) String phone,
                        @RequestParam(name = "pwd") String pwd, HttpServletRequest request, Model model) {

        User user = loginService.loginByaccountAndPwd(phone, pwd);
        //如果user为空，登录失败
        if (user == null) {
            model.addAttribute("msg", "登录失败");
            model.addAttribute("code", 0);
        } else {
            //如果user非空，登录成功
            model.addAttribute("msg", "登录成功");
            model.addAttribute("code", 1);
        }
        return "index";
    }

}
