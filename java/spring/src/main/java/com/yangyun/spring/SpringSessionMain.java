package com.yangyun.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description springsession 使用
 * @author yangyun
 * @date 2019/7/4 0004
 */
@SpringBootApplication
@MapperScan("com.yangyun.spring")
public class SpringSessionMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringSessionMain.class, args);
    }
}
