package com.yangyun.config;

import com.yangyun.aop.CalculateService;
import com.yangyun.aop.LogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName MainAopConfig
 * @Description:
 * @Author 86155
 * @Date 2020/1/19 10:02
 * @Version 1.0
 **/
@EnableAspectJAutoProxy // 开启基于注解版的aop功能
@Configuration
public class MainAopConfig {

    @Bean
    public CalculateService calculateService (){

        return new CalculateService();
    }

    @Bean
    public LogAspect logAspect (){
        return new LogAspect();
    }

}
