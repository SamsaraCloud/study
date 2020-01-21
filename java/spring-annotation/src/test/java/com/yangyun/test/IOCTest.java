package com.yangyun.test;

import com.yangyun.config.BeanConfiCycle;
import com.yangyun.config.MainConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName IOCTest
 * @Description:
 * @Author 86155
 * @Date 2020/1/17 10:24
 * @Version 1.0
 **/
public class IOCTest {

    @Test
    public void test02(){
        AnnotationConfigApplicationContext configApplicationContext =
                new AnnotationConfigApplicationContext(BeanConfiCycle.class);
//        configApplicationContext.getBean("car");
//        configApplicationContext.getBean("");
        configApplicationContext.close();
    }

    @Test
    public void test01(){
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Object person = configApplicationContext.getBean("person");
        Object person2 = configApplicationContext.getBean("person");
        System.out.println(person == person2);
    }
}
