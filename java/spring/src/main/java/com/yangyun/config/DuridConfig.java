package com.yangyun.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;

/**
 * 功能描述: 
 * @Param: test git commit
 * @Return: 
 * @Author: yangyun
 * @Date: 2020/5/11 20:18
 */
@Configuration
public class DuridConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDruid () {
        return new DruidDataSource();
    }
}
