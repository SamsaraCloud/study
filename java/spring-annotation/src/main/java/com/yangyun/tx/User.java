package com.yangyun.tx;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName User
 * @Description:
 * @Author 86155
 * @Date 2020/1/21 21:01
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class User {
    private String userName;
    private int age;
}
