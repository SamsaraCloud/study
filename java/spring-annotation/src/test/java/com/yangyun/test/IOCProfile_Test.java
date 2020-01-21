package com.yangyun.test;

import com.yangyun.config.MainConfigProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * @ClassName IOCProfile_Test
 * @Description:
 * @Author 86155
 * @Date 2020/1/18 15:58
 * @Version 1.0
 **/
public class IOCProfile_Test {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigProfile.class);

    @Test
    public void test01(){
        String[] beanNamesForType = context.getBeanNamesForType(DataSource.class);
        for (String name : beanNamesForType){
            System.out.println(name);
        }
    }
}
