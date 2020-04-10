# ActiveMQ< **5.15.11** >

解耦,削峰,异步

![](F:\git\study\java\MD\image\mq01.jpg)

![](F:\git\study\java\MD\image\mq02.jpg)

![](F:\git\study\java\MD\image\mq03.jpg)

![](F:\git\study\java\MD\image\mq04.jpg)

```shell
# 查看被占用端口的进程号
netstat -anp|grep 61616
lsof -i:61616
```

![](F:\git\study\java\MD\image\mq05.jpg)

![](F:\git\study\java\MD\image\mq06.jpg)

![](F:\git\study\java\MD\image\mq07.jpg)

![](F:\git\study\java\MD\image\mq08.jpg)

![](F:\git\study\java\MD\image\mq09.jpg)

![](F:\git\study\java\MD\image\mq10.jpg)

## JMS Message

### 消息头

#### JMSDestination

消息发送的目的地, 主要是指Queue和Topic

#### JMSDeliveryMode

持久和非持久模式

一条持久性的消息应该被传送"**一次仅仅一次**", 这就意味着如果JMS提供者出现故障, 该消息并不会丢失, 它会在服务器恢复之前再次传递

一条非持久的消息, 最多会传送给一次, 这意味着服务器出现故障, 该消息将永远丢失

#### JMSExpiration

可以设置消息在一段时间后过期. 消息过期时间, 等于Destination的send方法中timeToLive值加上发送时刻的SMT时间, 如果timeToLive等于0, 则JMSExpiration被设为0, 表示永不过期. 如果发送后, 在消息过期时间之后消息还没有被发送发送到目的地, 则该消息被清除

#### JMSPriority

消息优先级, 从0-9十个级别, 0到4是普通消息, 5到9是加急消息

JMS不要求MQ严格按照这十个优先级发送消息, 但必须保证加急消息要先于普通消息到达. 默认 4级

#### JMSMessageID

唯一识别每个消息的标识由MQ产生

### 消息体

封装具体的消息数据, 生产者和消费者在发送和接受消息的消息体类型必须一致对应

#### 五种消息体格式

##### TextMessage

普通字符串消息, 包含一个String

##### MapMessage

一个Map类型的消息, key为String类型, 值为java基本类型

##### BytesMessage

二进制数组消息, 包含一个byte[]

##### StreamMessage

java数据流消息, 用标准流操作来顺序的填充和读取

##### ObjectMessage

对象消息, 包含一个**可序列化**的java对象

### 消息属性

识别/去重/重点标注等操作

![](F:\git\study\java\MD\image\mq11.jpg)

## JMS 消息的可靠性

### 多节点集群

###  持久性Ppersist

队列默认为持久性消息

主题通过设置

```java
MessageProducer producer = session.createProducer(topic);
producer.setDeliveryMode(DeliveryMode.PERSISTENT);
```

### 事务

Session session = Connection.createSession(transcated, acknowledgeMode);

#### transcated: 是否事务 (偏生产者)

##### produce

false: 没有事务, 在生产者发送消息到队列时, 会默认提交 commit();

true: 有事务; 在生产者发送消息到队列时, 需要手动提交 session.commit(); 消息才能正常发送到队列

##### consumere

false: 队列中的消息可被消费仅有一次

true

当 consumer 中没有手动提交 session.commit(); 队列中的消息可以被重复消费

只有当 consumer 提交了 session.commit(); 队列中的消息才会被正常消费

### acknowledgeMode:  签收模式 (偏消费者)

#### 非事务

Session session = Connection.createSession(transcated, acknowledgeMode);

transcated=false;

自动签收 session.AUTO_ACKNOWLEDGE

手动签收 Session.CLIENT_ACKNOWLEDGE, 客户端调用 acknowledge 方法手动签收

```java
Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

while(true){
    TextMessage textMessage = (TextMessage)consumer.receive();
    if (null != textMessage){
        System.out.println("Consumer receive message ======> " + textMessage.getText());
        // 如果没有执行该方法, 队列中消息可被重复消费
        textMessage.acknowledge();
    }
}
```

允许重复消息 Session.DUPS_OK_ACKNOWLEDGE

#### 事务

```java
// produce
Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
Queue queue = session.createQueue(QUEUE_NAME);
MessageProducer producer = session.createProducer(queue);

for(int i = 1; i<=3; i++){
    TextMessage textMessage = session.createTextMessage("msg------" + i);

    producer.send(textMessage);
}
producer.close();
session.close();
connection.close();
System.out.println("===========");

// Consumer
Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
Queue queue = session.createQueue(QUEUE_NAME);

MessageConsumer consumer = session.createConsumer(queue);
while(true){
    TextMessage textMessage = (TextMessage)consumer.receive();
    if (null != textMessage){
        System.out.println("Consumer receive message ======> " + textMessage.getText());
        textMessage.acknowledge();
    } else {
        break;
    }
}
	// 在开启事务的情况下
	// 1. 在没有手动提交的情况下, 即使执行 textMessage.acknowledge(); 消息还是可以被重复消费,
	//    并不会正常被消费
	// 2. session.commit(); 手动提交后, 即使没有执行 textMessage.acknowledge(); 消息也能正常被消费
// session.commit();
```

### 事务和签收的关系

在事务性会话中, 当一个事务被成功提交则消息被自动签收, 如果事务回滚, 则消息会被再次传送

非事务性会话中, 消息何时被确认取决于创建会话时的应答模式(acknowledgement mode)

## JMS 点对点

### ![](F:\git\study\java\MD\image\mq12.jpg)

## JMS 发布订阅

![](F:\git\study\java\MD\image\mq13.jpg)

![](F:\git\study\java\MD\image\mq14.jpg)

## ActiveMQ Broker

![](F:\git\study\java\MD\image\mq14 (2).jpg)

通过执行指定的配置文件启动多个 ActiveMQ

![](F:\git\study\java\MD\image\mq15.jpg)

```SHELL
# 进入到 ActiveMQ 安装目录, activemq.xml 和 activemq02.xml 都为启动配置文件, 运行指定的配置文件
# ./activemq start xbean:file:/myactiveMQ/apache-activemq-5.15.11/conf/activemq{}.xml
./activemq start xbean:file:/myactiveMQ/apache-activemq-5.15.11/conf/activemq02.xml
```

## ActiveMQ传输协议

http://activemq.apache.org/configuring-transports.html

### tcp

![](F:\git\study\java\MD\image\m116.jpg)

### nio

![](F:\git\study\java\MD\image\mq16.jpg)

### amqp

![](F:\git\study\java\MD\image\mq17.jpg)

### stomp

![](F:\git\study\java\MD\image\mq18.jpg)

### ssl

Secure Sockeds Layer

![](F:\git\study\java\MD\image\mq19.jpg)

### mqtt

![](F:\git\study\java\MD\image\mq20.jpg)

### ws

websocked

![](F:\git\study\java\MD\image\mq21.jpg)

### auto+nio

```shell
<transportConnector name="auto+nio" uri="auto+nio://0.0.0.0:61610?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600&amp;org.apache.activemq.transport.nio.SelectorManager.corePoolSize=20&amp;org.apache.activemq.transport.nio.SelectorManager.maximumPoolSize=50"/>
```

## ActiveMQ的消息和持久化

![](F:\git\study\java\MD\image\mq22.jpg)

### AMQ Message Store

基于文件的存储方式, 是以前默认的消息存储, 现在不用了

![](F:\git\study\java\MD\image\mq23.jpg)

### KahaDB(默认)

基于日志文件. 从5.4开始默认的持久化插件

![](F:\git\study\java\MD\image\mq24.jpg)

![](F:\git\study\java\MD\image\mq25.jpg)

![](F:\git\study\java\MD\image\mq26.jpg)



### JDBC

##### 导入mysql-jar

导入 mysql-connector-java.jar 到ActiveMQ 安装目录的lib目录下

**说明: 如果是使用其他连接池, 还需要导入对应的jar才行**

![](F:\git\study\java\MD\image\mq28.jpg)

##### 配置 JDBC Persistence

![](F:\git\study\java\MD\image\mq29.jpg)

```java
## 默认为true, createTablesOnStartup= true 表示在每次 ActiveMQ 启动的时候回默认创建一些用来保存消息的表
## 在第一次的时候设置true 启动, 后面再修改为false
<persistenceAdapter> 
  <jdbcPersistenceAdapter dataSource="#mysql-ds" createTablesOnStartup="true"/> 
</persistenceAdapter>
```

##### 数据库连接池

```xml
<bean id="mysql-ds" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close"> 
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/> 
    <property name="url" value="jdbc:mysql://localhost/activemq?relaxAutoCommit=true"/> 
    <property name="username" value="activemq"/> 
    <property name="password" value="activemq"/> 
    <property name="poolPreparedStatements" value="true"/> 
    <property name="maxTotal" value="200"/>
</bean> 
```

##### 启动报错

只能创建两张表

![1580978807614](F:\git\study\java\MD\image\1580978807614.png)

**找不到这个错误的可以进入到 ActiveMQ 安装目录的 conf 目录下查看 log4j.properties** 

```properties
log4j.appender.logfile.file=${activemq.data}/activemq.log
```



```java
2020-02-06 16:20:23,183 | WARN  | JDBC Failure: Table 'activemq.ACTIVEMQ_ACKS' doesn't exist | org.apache.activemq.store.jdbc.JDBCPersistenceAdapter | main
com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Table 'activemq.ACTIVEMQ_ACKS' doesn't exist
	at sun.reflect.GeneratedConstructorAccessor4.newInstance(Unknown Source)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)[:1.8.0_181]
	at java.lang.reflect.Constructor.newInstance(Constructor.java:423)[:1.8.0_181]
	at com.mysql.jdbc.Util.handleNewInstance(Util.java:425)[mysql-connector-java-5.1.48.jar:5.1.48]
	at com.mysql.jdbc.Util.getInstance(Util.java:408)[mysql-connector-java-5.1.48.jar:5.1.48]
	at com.mysql.jdbc.SQLError.createSQLException(SQLError.java:944)[mysql-connector-java-5.1.48.jar:5.1.48]
	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3933)[mysql-connector-java-5.1.48.jar:5.1.48]
	at com.mysql.jdbc.MysqlIO.checkErrorPacket(MysqlIO.java:3869)[mysql-connector-java-5.1.48.jar:5.1.48]
	at com.mysql.jdbc.MysqlIO.sendCommand(MysqlIO.java:2524)[mysql-connector-java-5.1.48.jar:5.1.48]
	at com.mysql.jdbc.MysqlIO.sqlQueryDirect(MysqlIO.java:2675)[mysql-connector-java-5.1.48.jar:5.1.48]
	at com.mysql.jdbc.ConnectionImpl.execSQL(ConnectionImpl.java:2465)[mysql-connector-java-5.1.48.jar:5.1.48]
	at com.mysql.jdbc.PreparedStatement.executeInternal(PreparedStatement.java:1912)[mysql-connector-java-5.1.48.jar:5.1.48]
	at com.mysql.jdbc.PreparedStatement.executeQuery(PreparedStatement.java:2020)[mysql-connector-java-5.1.48.jar:5.1.48]
	at org.apache.commons.dbcp2.DelegatingPreparedStatement.executeQuery(DelegatingPreparedStatement.java:122)[commons-dbcp2-2.7.0.jar:2.7.0]
	at org.apache.commons.dbcp2.DelegatingPreparedStatement.executeQuery(DelegatingPreparedStatement.java:122)[commons-dbcp2-2.7.0.jar:2.7.0]
	at org.apache.commons.dbcp2.DelegatingPreparedStatement.executeQuery(DelegatingPreparedStatement.java:122)[commons-dbcp2-2.7.0.jar:2.7.0]
	at org.apache.activemq.store.jdbc.adapter.DefaultJDBCAdapter.doGetLastMessageStoreSequenceId(DefaultJDBCAdapter.java:185)[activemq-jdbc-store-5.15.11.jar:5.15.11]
	at org.apache.activemq.store.jdbc.JDBCPersistenceAdapter.getLastMessageBrokerSequenceId(JDBCPersistenceAdapter.java:260)[activemq-jdbc-store-5.15.11.jar:5.15.11]
	at org.apache.activemq.broker.region.DestinationFactoryImpl.getLastMessageBrokerSequenceId(DestinationFactoryImpl.java:147)[activemq-broker-5.15.11.jar:5.15.11]
	at org.apache.activemq.broker.region.RegionBroker.<init>(RegionBroker.java:130)[activemq-broker-5.15.11.jar:5.15.11]
	at org.apache.activemq.broker.jmx.ManagedRegionBroker.<init>(ManagedRegionBroker.java:108)[activemq-broker-5.15.11.jar:5.15.11]
```

网上说是数据库编码问题, 将编码修改为  **latin1**. 启动重新启动就可以了; 主要是 ACTIVEMQ_ACKS 表的编码问题

**那么我们在自己手动创建好三张表之后, 编码还是原来默认的是否可以呢?**

```sql
# 用于存储订阅关系. 持久化 topic
# 存储持久订阅的信息和最后一个持久订阅接收的消息id
CREATE TABLE `ACTIVEMQ_ACKS` (
  `CONTAINER` varchar(250) NOT NULL, # 消息 Destination
  `SUB_DEST` varchar(250) DEFAULT NULL,# 如果使用 Static 集群, 会有集群其他系统的信息. 集群环境
  `CLIENT_ID` varchar(250) NOT NULL,# 每个订阅者都有一个唯一的客户端id
  `SUB_NAME` varchar(250) NOT NULL,# 订阅者名称
  `SELECTOR` varchar(250) DEFAULT NULL,# 选择器, 可以选择消费满足条件的消息, 可以使用and和or操作
  `LAST_ACKED_ID` bigint(20) DEFAULT NULL,# 记录消费过的消息id
  `PRIORITY` bigint(20) NOT NULL DEFAULT '5',# 优先级 0-9 值越大越高
  `XID` varchar(250) DEFAULT NULL,# 事务id
  PRIMARY KEY (`CONTAINER`,`CLIENT_ID`,`SUB_NAME`,`PRIORITY`),
  KEY `ACTIVEMQ_ACKS_XIDX` (`XID`)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC DEFAULT CHARSET=utf8mb4;
# 该表只有在集群环境中才会使用, 只有一个 broker 可以获得消息, 成为 master broker, 其他 broker只能作为备份当 master broker 不可用, 才可能成为下一个 master. 该表用于记录当前 master broker 的信息
CREATE TABLE `ACTIVEMQ_LOCK` (
  `ID` bigint(20) NOT NULL,
  `TIME` bigint(20) DEFAULT NULL,
  `BROKER_NAME` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

## 消息表, queue 和 topic 的消息都保存在这里
CREATE TABLE `ACTIVEMQ_MSGS` (
  `ID` bigint(20) NOT NULL, # 自增数据库主键
  `CONTAINER` varchar(250) NOT NULL, # 消息 destination
  `MSGID_PROD` varchar(250) DEFAULT NULL, # 消息发送者的主键
  `MSGID_SEQ` bigint(20) DEFAULT NULL, #发送消息的顺序 MSGID_PROD + MSGID_SEQ 组成 MessageId
  `EXPIRATION` bigint(20) DEFAULT NULL,# 消息的过期时间, 存储的是从 1970-01-01 到现在的毫秒数
  `MSG` longblob, # 消息本体的 java 序列化对象的二进制数据
  `PRIORITY` bigint(20) DEFAULT NULL, # 优先级 0-9 值越大越高
  `XID` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ACTIVEMQ_MSGS_EIDX` (`EXPIRATION`),
  KEY `ACTIVEMQ_MSGS_PIDX` (`PRIORITY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
## MySQL 建索引时 Specified key was too long; max key length is 767 bytes 错误的处理
# 先检查一下是不是数据库被限制了索引的大小
SHOW variables like 'innodb_large_prefix';
# 如果查询的值是OFF的话 执行下面命令
SET GLOBAL INNODB_LARGE_PREFIX = ON;
# 执行完了 之后 还得查看当前的innodb_file_format引擎格式类型是不是BARRACUDA
SHOW variables like 'innodb_file_format';
# 如果不是的话则需要修改
SET GLOBAL innodb_file_format = BARRACUDA;
# 创建表的时候指定表的 row format 格式为 Dynamic 或者 Compressed，如下示例：
```

**表创建完记得修改 createTablesOnStartup="true" 属性值为 false**.

##### Queue

对于非持久化的消息, 不会保存到数据库中;

**对于持久化的消息, 已经消息完的消息, 数据库会自动删除**

![1580982969983](F:\git\study\java\MD\image\1580982969983.png)

##### Topic

对于发布订阅的 Topic 消息, **需要先启动订阅者, 如果先启动消息生产者**, 发送的消息不会被保存到数据库. 对于Topic 订阅的持久化消息, 及时被消费, 也不会被数据库删除.

##### 开发中可能遇到的问题

![1580994909835](F:\git\study\java\MD\image\1580994909835.png)

##### JDBC WIth Journal

###### 说明

![1580995179791](F:\git\study\java\MD\image\1580995179791.png)

###### 配置

![1580995274219](F:\git\study\java\MD\image\1580995274219.png)

```xml
<persistenceFactory>
	<journalPersistenceAdapterFactory 
	journalLogFiles="5" 
	journalLogFileSize="32768" 
    useJournal="true" 
    useQuickJournal="true" 
	dataDirectory="${activemq.data}/activemq-data" 
	dataSource="#mysql-ds"
	/>
</persistenceFactory>
```

重新启动 ActiveMQ, 可以在指定的目录看到 journal 的日志文件

![1580996221800](F:\git\study\java\MD\image\1580996221800.png)

## ActiveMQ 多节点集群

### zookeeper+replacated-levelDB-store主从集群

![1581313463965](F:\git\study\java\MD\image\1581313463965.png)

![1581486290506](F:\git\study\java\MD\image\1581486290506.png)

### zookeeper 集群(docker)

```shell
2181　　Zookeeper客户端交互端口
2888　　Zookeeper集群端口
3888　　Zookeeper选举端口
```

使用 docker-compose 完成 zookeeper 集群

参考[docker-hup](https://hub.docker.com/_/zookeeper)

在本机服务器新建 .yml 文件, **特别需要注意的是 .yml 文件的格式(java 中 yml)**

```yml
version: '3.1'
services:
  zoo1:
    image: zookeeper:3.4.11
    restart: always
    hostname: zoo1
    container_name: zookeeper_1
    ports:
    - 2181:2181
    volumes:
    - /usr/local/docker_app/zookeeper/zoo1/data:/data
    - /usr/local/docker_app/zookeeper/zoo1/datalog:/datalog
    environment:
      ZOO_MY_ID: 1
      ZOO_SERVERS: server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888
  zoo2:
    image: zookeeper:3.4.11
    restart: always
    hostname: zoo2
    container_name: zookeeper_2
    ports:
    - 2182:2181
    volumes:
    - /usr/local/docker_app/zookeeper/zoo2/data:/data
    - /usr/local/docker_app/zookeeper/zoo2/datalog:/datalog
    environment:
      ZOO_MY_ID: 2
      ZOO_SERVERS: server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888
  zoo3:
    image: zookeeper:3.4.11
    restart: always
    hostname: zoo3
    container_name: zookeeper_3
    ports:
    - 2183:2181
    ### 容器卷目录
    volumes:
    - /usr/local/docker_app/zookeeper/zoo3/data:/data
    - /usr/local/docker_app/zookeeper/zoo3/datalog:/datalog
    environment:
      ZOO_MY_ID: 3
      ZOO_SERVERS: server.1=zoo1:2888:3888 server.2=zoo2:2888:3888 server.3=zoo3:2888:3888
```

```shell
## 运行 .yml 文件, 后面添加 -d 表示后台运行, 我们第一次运行就不需要添加了, 需要查看日志是否正常启动
docker-compose -f zk-compose.yml up

## 如果无法使用 docker-compose 命令, 可以通过 pip 来安装
## 首先查看是否安装了 pip
pip -V
## 没有安装先安装
yum -y install epel-release
yum -y install python-pip

#######本机已经安装了pip 或者后面安装的, 需要更新 pip, 在没有更新pip版本而去安装docker-compose会报错
pip install --upgrade pip

##############此时 pip 完成, 网上很多在完成pip后会去安装 docker-compose########
#######郑重说明, 后面安装 docker-compose 会报错, 类似下面#########
############这些错误会导致 docker-compose 无法使用#########
########所以在更新完 pip 之后, 执行下面的命令安装 docker-compose
pip install docker-compose==1.23.2 -i http://pypi.douban.com/simple/ --trusted-host pypi.douban.com
```

```shell
Traceback (most recent call last):
  File "/usr/bin/docker-compose", line 10, in <module>
    sys.exit(main())
  File "/usr/lib/python2.7/site-packages/compose/cli/main.py", line 71, in main
    command()
  File "/usr/lib/python2.7/site-packages/compose/cli/main.py", line 124, in perform_command
    project = project_from_options('.', options)
```

```shell
Traceback (most recent call last):
  File "/usr/bin/docker-compose", line 6, in <module>
    from compose.cli.main import main
  File "/usr/lib/python2.7/site-packages/compose/cli/main.py", line 22, in <module>
    from ..bundle import get_image_digests
  File "/usr/lib/python2.7/site-packages/compose/bundle.py", line 13, in <module>
    from .network import get_network_defs_for_service
  File "/usr/lib/python2.7/site-packages/compose/network.py", line 9, in <module>
    from docker.types import IPAMConfig
ImportError: cannot import name IPAMConfig
```

```shell
Traceback (most recent call last):
  File "/usr/bin/docker-compose", line 7, in <module>
    from compose.cli.main import main
  File "/usr/lib/python2.7/site-packages/compose/cli/main.py", line 17, in <module>
    import docker
ImportError: No module named docker
```

**如果是已经安装了 docker-compose 而导致无法使用**

```shell
## 1. 使用pip list|grep docker查看已安装相关docker包信息
## 2. 手动卸载第一步列出来的包
pip uninstall docker
pip uninstall docker-py
pip uninstall dockerpty
....
## 3. 安装docker-compose
pip install docker-compose==1.23.2 -i http://pypi.douban.com/simple/ --trusted-host pypi.douban.com
```

```shell
### 进入到 .yml 目录, 执行 docker-compose -f xxx.yml up 启动 zookeeper 集群
### 查看容器启动情况
docker-compose -f xxx.yml ps

### 下面是我本机启动后的信息
[root@izwz94664y88uglloijjy9z /]# docker-compose -f docker-compose.yml ps
   Name                  Command               State                     Ports                   
-------------------------------------------------------------------------------------------------
zookeeper_1   /docker-entrypoint.sh zkSe ...   Up      0.0.0.0:2181->2181/tcp, 2888/tcp, 3888/tcp
zookeeper_2   /docker-entrypoint.sh zkSe ...   Up      0.0.0.0:2182->2181/tcp, 2888/tcp, 3888/tcp
zookeeper_3   /docker-entrypoint.sh zkSe ...   Up      0.0.0.0:2183->2181/tcp, 2888/tcp, 3888/tcp
```

```shell
### 可以进入到 zk 后端, 通过命令查看那个 zk 为 master
docker exec -it zookeeper_1 /bin/bash
bash-4.4# ls
README.txt    zkCleanup.sh  zkCli.cmd     zkCli.sh      zkEnv.cmd     zkEnv.sh      zkServer.cmd  zkServer.sh
bash-4.4# zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /conf/zoo.cfg
Mode: leader
bash-4.4# 
```

启动完成可以通过 zookeeper 客户端工具连接

![1581567356556](F:\git\study\java\MD\image\1581567356556.png)

### ActiveMQ 集群(docker)

下载 ActiveMQ 镜像, 通过 docker search activemq 查找镜像. 下载 webcenter/activemq

根据下载的镜像运行容器

```shell
docker run --name mqnode01 -p 61612:61616 -p 8162:8161 -d webcenter/activemq
docker run --name mqnode02 -p 61613:61616 -p 8163:8161 -d webcenter/activemq
docker run --name mqnode03 -p 61614:61616 -p 8164:8161 -d webcenter/activemq
```

如果是部署在阿里云服务器或别云服务器, 还需要开放对应的端口

容器启动完, 修改对应 mq 的配置文件

```shell
## 进入到容器
docker exec -it mqnode01 /bin/bash
### 容器中 mq 目录, 需要进入到 conf 目录修改其中的 activemq.xml 配置
```

![1581567847253](F:\git\study\java\MD\image\1581567847253.png)

配置修改

因为是集群, 所以 所有 mq 的名称都需一致

```xml
## mqnode01, mqnode02, mqnode03 修改activemq.xml 中 brokerName, 名字自定义
<broker xmlns="http://activemq.apache.org/schema/core" brokerName="localhost" dataDirectory="${activemq.data}">
### 修改持久化配置, 注释默认的 kahaDB
<persistenceAdapter>
	<kahaDB directory="${activemq.data}/kahadb"/>
</persistenceAdapter>
####### 使用 Replicated LevelDB Store
### 具体配置可以参考 http://activemq.apache.org/replicated-leveldb-store
### sync="local_disk" 此处配置不要忘, 集群环境没有此配置, 虽然能启动也能连接到 zk, 却不能正常访问 
### mqnode01=63631 mqnode02=63632 mqnode03=63633
### hostname="mq-server" 为本地 hosts ip映射
<persistenceAdapter>
  <replicatedLevelDB
  directory="${activemq.data}/leveldb"
  replicas="3"
  bind="tcp://0.0.0.0:63631" 
  zkAddress="172.18.137.166:2181,172.18.137.166:2182,172.18.137.166:2183"
  zkPath="/activemq/leveldb-stores"
  sync="local_disk"  
  hostname="mq-server"
  />
</persistenceAdapter>
```

![1581568461243](C:\Users\86155\AppData\Roaming\Typora\typora-user-images\1581568461243.png)

因为我们是使用的 docker 启动, 已经制定了端口, 所以 61616 和 8161 的端口不需要再修改;

所有配置修改完成, 停止 mqnode01/02/03 容器, 然后在启动; 然后通过 zk 客户端工具连接到任意 zk server, 就可以看到所有 mq 节点了, 可以看到具体的几点信息, 其中 elected 不为 null 的几点为 master 其余为 slave

![1581569700232](F:\git\study\java\MD\image\1581569700232.png)

![1581569709571](F:\git\study\java\MD\image\1581569709571.png)

```shell
## 也可以进入到 zk 的后端
docker exec -it zookeepere_1 /bin/bash
### 进入到 zk bin目录, 执行 ./zkCli.sh -server ip:port 进入 zk 集群中任意个, 
### 其中 ip 可以是公网ip 或者私有id (对于云服务), 也可以使用 127.0.0.1 但是只能访问第一个 zk serve
```

![1581568961481](C:\Users\86155\AppData\Roaming\Typora\typora-user-images\1581568961481.png)

```shell
### 进入到指定 zk 客户端命令行后, 通过命令查看到两个服务 mq 和 zk
ls /
[activemq, zookeeper]
## 通过 mq 中配置文件中 zkPath="/activemq/leveldb-stores" 可以查看 mq 节点
ls /activemq/leveldb-stores
[00000000028, 00000000026, 00000000027] ## 应该从0 开始, 因为我启动停止了好多次, 所以数字累加了
##  通过 get 命令查看具体节点信息
get /activemq/leveldb-stores/[00000000028, 00000000026, 00000000027 中任意个]
### 其中 elected 不为 null 的几点为 master 其余为 slave
```

![1581569995128](F:\git\study\java\MD\image\1581569995128.png)

因为 mq集群只能访问 master,  mqnode01=61631 => 8162, 然后通过浏览器访问控制台, 8162 可以正常访问, 8163/8164  已经无法访问

![1581570187406](F:\git\study\java\MD\image\1581570187406.png)

![1581570211637](F:\git\study\java\MD\image\1581570211637.png)

通过后端java程序发送消息到mq

```java
// 将原先的地址改为如下, 然后运行程序
public static final String DEFAULT_BROKER_URL = "failover:(tcp://47.107.172.70:61612,tcp://47.107.172.70:61613,tcp://47.107.172.70:61614)?randomize=false";
```

![1581570346534](F:\git\study\java\MD\image\1581570346534.png)

![1581570363315](F:\git\study\java\MD\image\1581570363315.png)

再消费者

![1581570443992](F:\git\study\java\MD\image\1581570443992.png)

所有一切完工; **后面说明一下, 在使用docker 集成 mq 和 zk 集群的时候, 在使用 ip 地址时, 如果 127.0.0.0或者 0.0.0.0 或者 云服务器的公网 ip 和内网 ip, 都可以试一下**

## ActiveMQ 高级特性

### Async Send

![1581589556207](F:\git\study\java\MD\image\1581589556207.png)

![1581590200476](F:\git\study\java\MD\image\1581590200476.png)

### 延时/定时投递

 http://activemq.apache.org/delay-and-schedule-message-delivery 

![1581590885945](F:\git\study\java\MD\image\1581590885945.png)

### 分发策略

### 消费重试机制

![1581591833823](C:\Users\86155\AppData\Roaming\Typora\typora-user-images\1581591833823.png)

 http://activemq.apache.org/redelivery-policy 

![1581592149442](F:\git\study\java\MD\image\1581592149442.png)

![1581592290332](F:\git\study\java\MD\image\1581592290332.png)

### 死信队列

 http://activemq.apache.org/message-redelivery-and-dlq-handling 

![1581593090913](F:\git\study\java\MD\image\1581593090913.png)

![1581593141165](F:\git\study\java\MD\image\1581593141165.png)

![1581593194489](F:\git\study\java\MD\image\1581593194489.png)

![1581593416183](F:\git\study\java\MD\image\1581593416183.png)

![1581593461418](F:\git\study\java\MD\image\1581593461418.png)

![1581593498025](F:\git\study\java\MD\image\1581593498025.png)

![1581593586452](F:\git\study\java\MD\image\1581593586452.png)

![1581593624995](F:\git\study\java\MD\image\1581593624995.png)

### 保证消息不被重复消费, 幂等性问题

![1581593745858](F:\git\study\java\MD\image\1581593745858.png)





C:\Users\86155\AppData\Roaming\Typora\typora-user-images



