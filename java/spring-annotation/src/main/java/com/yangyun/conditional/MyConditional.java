package com.yangyun.conditional;


import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @ClassName MyConditional
 * @Description:
 * @Author 86155
 * @Date 2020/1/17 11:09
 * @Version 1.0
 **/
public class MyConditional implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        ResourceLoader resourceLoader = context.getResourceLoader();
        BeanDefinitionRegistry registry = context.getRegistry();
        return false;
    }
}
