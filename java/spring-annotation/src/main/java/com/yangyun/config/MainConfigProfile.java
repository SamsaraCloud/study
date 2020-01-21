package com.yangyun.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @ClassName MainConfigProfile
 * @Description: 以数据源为例
 *  @Profile: profile Spring 为我们提供的可以根据当前环境, 动态切换和激活一系列组件的功能
 * @Author 86155
 * @Date 2020/1/18 15:26
 * @Version 1.0
 **/
@PropertySource("classpath:db.properties")
@Configuration
public class MainConfigProfile implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    @Value("${db.url}")
    private String url;

    private String driverClass;

    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl(url + "test");
        dataSource.setDriverClass(driverClass);
        return null;
    }

    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl(url + "dev");
        dataSource.setDriverClass(driverClass);
        return null;
    }

    @Profile("prodDataSource")
    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl(url + "prod");
        dataSource.setDriverClass(driverClass);
        return null;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.driverClass = resolver.resolveStringValue("${db.driver}");
    }
}
