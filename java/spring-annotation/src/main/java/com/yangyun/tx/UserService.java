package com.yangyun.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UserService
 * @Description:
 * @Author 86155
 * @Date 2020/1/21 21:02
 * @Version 1.0
 **/
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void insertUser(User user){
        userDao.insertUser(user);
        System.out.println("新增成功...");
//        int a = 10/0;
    }
}
