package com.yangyun.jvm;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName MetaspaceOOMDemo
 * @Description: java.lang.OutOfMemoryError:metaspace
 * @Author 86155
 * @Date 2019/8/26 22:36
 * @Version 1.0
 **/
public class MetaspaceOOMDemo {
    // -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m

    static class OOMTest {}

    public static void main(String[] args) {
        int i = 0;
        try {
            while (true){
                i++;
                // cglib 动态代理主类
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invoke(o, objects);
                    }
                });
                enhancer.create();
            }
        } catch (Throwable e) {
            System.out.println("=============" + i);
            e.printStackTrace();
        }
    }
}
