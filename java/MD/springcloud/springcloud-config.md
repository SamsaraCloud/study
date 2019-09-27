###sprigcloud config server 连通 githup

1. 新建 repository microservicecloud-config

2. 获取 ssh 协议的 git 地址 https://github.com/SamsaraCloud/microservicecloud-config.git

3.  在本地目录新建git 仓库并 clone

   1. F:\git\microservicecloud-config  = F:\git\microservicecloud-config\microservicecloud-config
   2. git 命令 git clone https://github.com/SamsaraCloud/microservicecloud-config.git

4. 在本地目录 F:\git\microservicecloud-config 里面新建一个 application.yml (**改文件要以 utf-8 的格式保存**)

   ```yml
   spring: 
     profiles: 
       active: 
       - dev
   ---
   spring: 
     profiles: dev      #开发环境
     application: 
         name: microservicesloud-config-yangyun-dev
   ---
   spring: 
     profiles: test  # 测试环境
     application: 
       name: microservicesloud-config-yangyun-test
   ```

5. 将yml 上传到 githup

   ![image/1568253786(1).jpg](image/1568253786(1).jpg)

6. 新建 module microservicecloud-config-3344 为 Cloud 配置中心模块

7. pom

   ```pom
   <dependencies>
           <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-config-server -->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-config-server</artifactId>
           </dependency>

           <!-- https://mvnrepository.com/artifact/org.eclipse.jgit/org.eclipse.jgit -->
           <!-- eclipse 避免Config的Git插件报错：org/eclipse/jgit/api/TransportConfigCallback -->
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-config</artifactId>
           </dependency>

           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-jetty</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
           </dependency>
       </dependencies>
   ```

8. yml

9. 主启动类 Config_3344_StartSpringCloudApp

10. windows 修改hosts (C:\Windows\System32\drivers\etc\hosts)文件增加映射, linux下 /etc/hosts文件

  1. 127.0.0.1 config-3344.com

11. 测试通过 Config 微服务是否可以从 githup 上获取配置内容

    1. 启动微服务 3344
    2. http://config-3344.com:3344/applicatoin-test.yml

12. 配置读取规则

    1. host:port/{application}-{profile}.yml = http://config-3344.com:3344/applicatoin-test.yml

    2. host:port/{application}/{profile}/{label} = http://config-3344.com:3344/applicatoin/test/master 

    3. host:port/{label}/{application}-{profile}.yml = http://config-3344.com:3344/master/applicatoin-test.yml

       ?

### SpringCloud Config 客户端配置

1. F:\git\microservicecloud-config\microservicecloud-config 下创建文件 microservicecloud-config-client.yml

   ```yml
   spring: 
     profiles: 
     active: 
     -dev
     
   ---
   server: 
     port: 8201
   spring: 
     profiles: dev
     application: 
       name: microservicecloud-config-client
   eureka: 
     client: 
       service-url: 
         defaultZone: http://eureka-dev.com:7001/eureka/

   ---
   server: 
     port: 8202
   spring: 
     profiles: test
     application: 
       name: microservicecloud-config-client
   eureka: 
     client: 
       service-url: 
         defaultZone: http://eureka-test.com:7001/eureka/
   ```

2. 提交文件到 githup

3. 新建 microservice-config-client-3355

4. pom

5. bootstrap.yml

   1. application.yml 是用户级别的资源配置

   2. bootstrap.yml 是系统级别的资源配置, 优先级更高

   3. Spring Cloud 会创建一个 Bootstrap Context, 作为 Spring 应用的 Application Context的 **父上下文**. 初始化的时候, Bootstrap Context 负责从外部资源加载配置属性并解析配置, 这两个上下文共享一个从外部获取的 Enviroment. Bootstrap 属性有高优先级, 默认情况下, 他们不会被本地配置覆盖, Bootstrap Context 和 Application Context 有着不同的约定, 所以新增一个 bootstrap.yml 文件, 保证 Bootstrap Context 和 Application Context 配置的分离

      ```yml
      # spring config client 端
      spring:
        cloud:
          config:
            name: microservicecloud-config-client  # 从 git 上读取的 client 端资源文件, 没有 .yml 后缀名
            profile: dev #  本次访问的配置项
            label: master     # git 上传所在分支, 默认 master
            uri: http://config-3344.com:3344           # 本微服务启动后先去找 config server 3344 号服务, 通过 SpringCloudConfig 获取 githup 的服务地址
        
      ```

6. application.yml

   ```yml
   spring:
     application:
       name: microservicecloud-config-client
   ```

7. 修改hosts增加映射

   1. 127.0.0.1  client-config.com

8. 新建 rest 类, 验证是否能从 githup 上读取配置

   1. ConfigClientRest

9. 主启动类 ConfigClient_3355_StartSpringCloudApp

10. 测试

   1. http://client-config.com:8201/api/config

### SpringCloud Config 配置实战

1. 本地配置

   1. F:\git\microservicecloud-config\microservicecloud-config 下
      1. 新建文件 microservicecloud-config-eureka-config.yml
      2. 新建文件 microservicecloud-config-dept-client.yml

2. 新建 microservicecloud-config-eureka-client-7001

3. pom

   ```xml
       <dependencies>
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-config</artifactId>
           </dependency>

           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
           </dependency>
       </dependencies>
   ```

4. bootstrap.yml

   ```yml
   spring:
     cloud:
       config:
         name: microservicecloud-config-eureka-client # 需要从githup 上湖区的资源名称
         profile: dev
         label: master
         uri: http://config-3344.com:3344  # config server
   ```

5. application.yml

   ```yml
   spring:
     application:
       name: microservicecloud-config-eureka-client
   ```

6. 测试

   1. 启动 microservicecloud-config-3344 config 配置中心
   2. 启动配置版 eureka server microservicecloud-config-eureka-client-7001 

7.  配置版 Config dept 微服务提供者

   1. 新工程 microservicecloud-config-dept-client-8001
   2. bootstrap.yml
   3. application.yml

8. 的

9. ?

10.   

11.  

12.  

13.  

14.  

15.  

16. ?

    ?