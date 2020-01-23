package com.yangyun.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserDao
 * @Description:
 * @Author 86155
 * @Date 2020/1/21 21:02
 * @Version 1.0
 **/
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertUser(User user){
        String sql = "insert into tbl_user(username, age) values(?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getAge());
    }
}
