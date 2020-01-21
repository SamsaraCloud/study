package com.yangyun.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @ClassName BusDisposableBean
 * @Description:
 * @Author 86155
 * @Date 2020/1/17 16:40
 * @Version 1.0
 **/
@Component
public class BusDisposableBean implements DisposableBean, InitializingBean {

    public BusDisposableBean(){
        System.out.println("BusDisposableBean construct....");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("BusDisposableBean destroy...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("BusDisposableBean  afterPropertiesSet");
    }
}
