package com.yangyun.config;

import com.yangyun.bean.Person;
import com.yangyun.imports.MyImportSelector;
import org.springframework.context.annotation.*;

/**
 * @ClassName MainConfig
 * @Description:
 * @Author 86155
 * @Date 2020/1/17 10:22
 * @Version 1.0
 **/
@Configuration
@Import({Person.class, MyImportSelector.class})
public class MainConfig {
    /**
     * @Author yangyun
     * @Description:
     *  Scope: 表示加载到容器中实例对象的作用域
     *      SCOPE_PROTOTYPE: 表示多实例, 每次都会新创建一个实例
     *          多实例模式下, 容器启动并不会加载实例, 只会在使用的时候才创建
     *      SCOPE_SINGLETON: 默认单列
     *          单列模式, 在容器启动的时候就会创建实例对象加载到容器中, 使用的时候直接使用已经创建好的实例对象
     *      SCOPE_REQUEST: 同意请求域只创建一个
     *      SCOPE_SESSION: 同一个session域只创建一个
     * @Date 2020/1/17 10:32
     * @Param []
     * @returnm com.yangyun.bean.Person
     **/
//    @Scope(value = "prototype")
    @Bean
//    @Lazy
    public Person person (){
        System.out.println("创建实例....");
        return new Person("张三", 25);
    }
}
