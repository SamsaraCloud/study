package com.yangyun.test;

import com.yangyun.tx.MainConfigTx;
import com.yangyun.tx.User;
import com.yangyun.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

/**
 * @ClassName TxTest
 * @Description:
 * @Author 86155
 * @Date 2020/1/21 21:37
 * @Version 1.0
 **/
public class TxTest {

    @Test
    public void test(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigTx.class);
        UserService userService = context.getBean(UserService.class);

        User user = new User();
        user.setUserName(UUID.randomUUID().toString().substring(0, 5)).setAge(15);

        userService.insertUser(user);
    }
}
