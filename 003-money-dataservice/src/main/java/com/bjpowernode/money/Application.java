package com.bjpowernode.money;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//这两个注解忘了需要看一下
@MapperScan("com.bjpowernode.money.mapper")
@EnableDubboConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
