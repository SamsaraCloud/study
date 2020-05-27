package com.yangyun.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyun
 * @Description:
 * @date 2020/5/26 18:16
 */
public class Main {

    public static void main(String[] args) throws  Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("mybatis-generator.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);

        MyMyBatisGenerator myBatisGenerator = new MyMyBatisGenerator(config, callback, warnings, true, true);
        myBatisGenerator.generate(null);

        AutoGenerator ag = new AutoGenerator();

        ag.execute();
    }
}
