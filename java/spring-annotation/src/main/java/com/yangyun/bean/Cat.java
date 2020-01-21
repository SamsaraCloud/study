package com.yangyun.bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ClassName Cat
 * @Description:
 * @Author 86155
 * @Date 2020/1/17 16:51
 * @Version 1.0
 **/
@Component
public class Cat {

    public Cat (){
        System.out.println("Cat constructor ...");
    }

    @PostConstruct
    public void init(){
        System.out.println("Cat @PostConstruct...");
    }

    @PreDestroy
    public void destroy(){

        System.out.println("Cat @PreDestroy...");
    }
}
