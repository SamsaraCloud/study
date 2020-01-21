package com.yangyun.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * @ClassName LogAspect
 * @Description:
 * @Author 86155
 * @Date 2020/1/19 10:04
 * @Version 1.0
 **/
@Aspect // 标注为切面类
public class LogAspect {

    /**
     * @Author yangyun
     * @Description:  公共的切入点表达式; 表示在方法执行时触发CalculateService下任意方法, 任意参数, 任意返回值类型
     * @Date 2020/1/19 10:14
     * @Param [point]
     * @returnm void
     **/
    @Pointcut("execution(public * com.yangyun.aop.CalculateService.*(..))")
    public void pointCut(){

    }


    @Before("pointCut()")
    public void logStart(JoinPoint point){
        Object[] args = point.getArgs();
        System.out.println("@Before执行方法: " + point.getSignature().getName() + ", 参数{"+ Arrays.asList(args) +"}");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint point){
        System.out.println("@After执行方法: " + point.getSignature().getName());
    }

    // result 为指定的接收方法的返回值
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturning(JoinPoint point, Object result){

    }

    // exception 目标方法出现异常时接收
    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logThrowing(JoinPoint point, Exception exception){
        System.out.println("@AfterThrowing"+point.getSignature().getName()+"异常。。。异常信息：{"+exception+"}");
    }
}
