package com.yangyun.aop;

import org.springframework.stereotype.Service;

/**
 * @ClassName CalculateService
 * @Description:
 * @Author 86155
 * @Date 2020/1/19 10:03
 * @Version 1.0
 **/
@Service
public class CalculateService {

    public int div(int i, int j){
        System.out.println("processing target method..");
        return i/j;
    }
}
