package com.yangyun.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Person
 * @Description:
 * @Author 86155
 * @Date 2020/1/17 10:21
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String name;
    private int age;
}
