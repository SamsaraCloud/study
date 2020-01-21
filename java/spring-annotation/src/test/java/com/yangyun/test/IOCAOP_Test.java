package com.yangyun.test;

import com.yangyun.aop.CalculateService;
import com.yangyun.config.MainAopConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName IOCAOP_Test
 * @Description:
 * @Author 86155
 * @Date 2020/1/19 10:00
 * @Version 1.0
 **/
public class IOCAOP_Test {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainAopConfig.class);

        CalculateService bean = context.getBean(CalculateService.class);
        bean.div(1, 1);

        context.close();
    }
}
