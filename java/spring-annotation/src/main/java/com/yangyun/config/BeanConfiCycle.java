package com.yangyun.config;

import com.yangyun.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @ClassName BeanConfiCycle
 * @Description: bean 生命周期
 * @Author 86155
 * @Date 2020/1/17 15:19
 * @Version 1.0
 **/
@Configuration
@ComponentScan("com.yangyun.bean")
public class BeanConfiCycle {

    @Scope("prototype")
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car(){

        return new Car();
    }
}
