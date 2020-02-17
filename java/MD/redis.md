#  Redis

[个版本下载](http://download.redis.io/releases/)

Redis 是一个缓存数据库, 可以承受 10W/s 的访问量, 同时Redis 还可以将数据进行持久化存储. 在Redis 关闭后数据也可以保留下来

![1581671862493](F:\git\study\java\MD\image\1581671862493.png)

![1581672041916](F:\git\study\java\MD\image\1581672041916.png)

![1581672646104](F:\git\study\java\MD\image\1581672646104.png)

## Redis 哨兵模式

![1581673454433](F:\git\study\java\MD\image\1581673454433.png)

### 启动 Redis 服务

根据 redis 不同的配置文件, 在一台主机上启动提供多个redis服务

修改 .conf 文件(**阿里云服务器**)

先查看ip地址

![1581756112250](F:\git\study\java\MD\image\1581756112250.png)

```
## ip 可以使用 127.0.0.1 或者云服务的内网ip, 外网ip的话无法启动
bind ip
## 关闭保护模式, 不然 sentinel 无法正常监听, 当master宕机后, 无法正常选举新的master
protected-mode no
## 原始 redis.pid; 根据启动端口修改为对应的
pidfile /var/run/redis_6379.pid
## 设置密码
requirepass yangyunjava
## 没有配置, 当master下线, 重新上线只有, 新的master无法识别， 值与密码一致
masterauth yangyunjava

#########slave conf 配置 master
slaveof 172.18.137.166 6379
```

```shell
/usr/local/redis/src/redis-server /usr/local/redis/config/redis-6379.conf
/usr/local/redis/src/redis-server /usr/local/redis/config/redis-6380.conf
/usr/local/redis/src/redis-server /usr/local/redis/config/redis-6381.conf
```

### 拷贝redis-sentinel 运行文件

```shell
cp /usr/local/redis/src/redis-sentinel /usr/local/redis/bin/
```

### 准备 sentinel 配置文件

```shell
## sentinel 默认端口 26379
## sentinel.conf  /usr/local/redis/sentinel.conf 参考模板
```

### 配置sentinel.conf 文件

```shell
/usr/local/redis/config/sentinel-26379.conf
/usr/local/redis/config/sentinel-26380.conf
/usr/local/redis/config/sentinel-26381.conf
```

```shell
######### sentinel 配置文件模板
## sentinel port
port 26379
## sentinel work place
dir /usr/data/redis/sentinel-26379
## 关闭保护模式
protected-mode no
## 因为需要看到日志, 关闭后台运行
daemonize no
## 监听的 redis master 2 表示如果有两个哨兵认为当前master出现问题, 则可以下线当前master选举新master
sentinel monitor mymaster ip(外网ip) 6379 2
## master 认证信息, 与 master 的 redis.conf 中 masterauth 的属性的值yagyunjava 一致
## mymaster 只是一个名称, 如果监听多个 master 这个名称必须不同
sentinel auth-pass mymaster yangyunjava
## master不活跃时间, 超过 30s 没有响应认为下线
sentinel down-after-milliseconds mymaster 30000
## 选举新master失败时间, 超过时间, 即选举失败
sentinel failover-timeout mymaster 180000
## 选举新master以后, slave 对新master同步的个数
## 这个配置项指定了在发生failover主备切换时最多可以有多少个slave同时对新的master进行 同步，这
## 个数字越小，完成failover所需的时间就越长，但是如果这个数字越大，就意味着越 多的slave因为replication
## 而不可用。可以通过将这个值设为 1 来保证每次只有一个slave 处于不能处理命令请求的状态。
sentinel parallel-syncs mymaster 1
```

### 启动 senetinel

```shell
/usr/local/redis/bin/redis-sentinel /usr/local/redis/config/sentinel-26379.conf
/usr/local/redis/bin/redis-sentinel /usr/local/redis/config/sentinel-26380.conf
/usr/local/redis/bin/redis-sentinel /usr/local/redis/config/sentinel-26381.conf
```

![1581756705139](F:\git\study\java\MD\image\1581756705139.png)

```
"+slave" 当哨兵启动后如果连接到master, 则自动追加所有的slave
"+sentinel" 没启动一个 sentinel 会自动打印提示信息
```

```shell
## 通过命令查看redis 信息
/usr/local/redis/src/redis-cli -h ip(bind ip) -p 6381 info replication
```

```
## 下线master
## sentinel 控制台
+sdown master mymaster ip 6379 表示master已经下线
+try-failover master mymaster 尝试做失败切换
+vote-for-leader a40be4c042d7127b8392ffdd6f33fdb09288ef20 1 进行新master的投票选举
+slave-reconf-sent slave 47.107.172.70:6380 47.107.172.70 6380 从主机的从新配置, 在这里会自动修改redis.conf配置文件,导致配置文件紊乱,不利于维护
```

```
+switch-master mymaster 47.107.172.70 6379 172.18.137.166 6381 选择的新master
```

![1581757513968](F:\git\study\java\MD\image\1581757513968.png)



## Redis Twenproxy

不管你的机器性能有多么牛逼, 一台 Redis 数据库的性能终归是有限的, 为了保证程序的运行速度和用户的执行数据, 就需要使用集群的设计. 集群主要解决的问题就是单实例 Redis 的性能瓶颈.

### Twenproxy

是一个专门为了 Nosql 数据库设计的一款代理工具, 这个工具的最大特征就是可以实现数据的分片处理. 所谓的分片就是根据一定的算法将我们要保存的数据保存到不同的节点当中。

有了分片之后数据的保存节点就有了无限可能，但是理论上正要进行集群的搭建，往往要求三台节点起步。能参与分片的节点一定是master。master后面会跟有多个slave。

![1581762612532](F:\git\study\java\MD\image\1581762612532.png)

![1581762667032](F:\git\study\java\MD\image\1581762667032.png)

![1581762708833](F:\git\study\java\MD\image\1581762708833.png)

#### 安装

```shell
## 进入源码安放目录
cd /usr/local/src
## 安装包下载
wget https://github.com/twitter/twemproxy/archive/v0.4.0.tar.gz
## 解压文件
tar -zxvf v0.4.0.tar.gz
## 进入到 twenproxy 目录
cd twemproxy-0.4.0
## 使用 autoreconf 工具生成一些编译的程序文件
## 首先安装 autoreconf 等工具
yum -y install autoconf automake libtool libffi-dev
## 进入twenproxy 目录执行
autoreconf -fvi
## 创建 twenproxy 编译后的工作目录
mkdir -p /usr/local/twenproxy
## 进行编译目录的配置， 也就是说，我编译后的程序都进入到 /usr/local/twenproxy/ 目录
./configure --prefix=/usr/local/twenproxy/
## 进行源代码的编译和安装
make && make install
#########################twenproxy 安装完成###########

## 准备 twenproxy 的配置文件，考虑到与其他机制的整合，名称一定要设置为 redis_master.conf
## 创建配置文件目录
mkdir -p /usr/local/twenproxy/conf
## 复制配置文件到 /usr/local/twenproxy/conf
cp /usr/local/src/twemproxy-0.4.0/conf/nutcracker.yml /usr/local/twenproxy/conf/redis_master.conf
## 配置文件修改
vim /usr/local/twenproxy/conf/redis_master.conf
## 只保留如下部分
## 修改前
alpha:
  listen: 127.0.0.1:22121
  hash: fnv1a_64
  distribution: ketama
  auto_eject_hosts: true
  redis: true
  server_retry_timeout: 2000
  server_failure_limit: 1
  servers:
   - 127.0.0.1:6379:1
## 修改后
redis_master:
  listen: 0.0.0.0:22121
  hash: fnv1a_64
  distribution: ketama
  auto_eject_hosts: true
  redis: true
  redis_auth: yangyunjava
  server_retry_timeout: 2000
  server_failure_limit: 1
  servers:
   - 47.107.172.70:6379:1
   - 172.31.96.247:6379:1
#  - 127.0.0.1:6379:1
#  - 127.0.0.1:6379:1
```

以上配置，配置了redis访问的密码和twenproxy所有可能代理的redis master节点

#### 启动

```shell
## 准备 pid和log目录
mkdir -p /usr/local/twenproxy/{pid,log}
## 启动
/usr/local/twenproxy/sbin/nutcracker /usr/local/twenproxy/conf/redis_master.conf
```

# Redis

```
1. 数据库表很大， 性能下降？
如果表有索引， 对于增删改，变慢，因为要维护索引。
2. 查询速度？
一个或少量查询依然很快
并发大的时候，会受磁盘带宽的影响而影响速度
```

## Redis安装

```shell
# 1. 安装 wget
yum install wget
# 2. 安装包获取, 安装包地址获取 https://redis.io/ => Download it
wget http://download.redis.io/releases/redis-5.0.7.tar.gz
# 3. 源码放置目录 /usr/local/src，解压安装包，不适用 vxf 省略了输出，减少 io
tar xf redis-5.0.7.tar.gz
# 4. 阅读 README.md 文件，里面有详细安装说明
# 5. 进入到 redis 根目录，安装，在安装前可以知道 src 目录没有 redis-server 等可执行文件
make
# 6. 如果报错，提示 gcc 没有安装，先安装 gcc
yum install gcc
# 7. 因为上一步 make 报错，需要清除错误的安装信息
make distclean
# 8. 从新执行安装命令
make
# 9. cd src 可以发现生成了可执行文件，此时就可以通过 redis-server 等可执行文件运行 redis 了
./redis-server
# 10. 将 redis 安装到系统，进入到redis根目录，在opt目录下新建tools,redis5系统会自动创建
make install PREFIX=/opt/tools/redis5
# 11. 进入 /opt/tools/redis5/bin 可以看到redis可执行文件
# 12. 将 redis 安装为系统服务，根据 READM.md 文件提示，需要进入到人redis根目录的utils目录下，执行install_server.sh
# 13. 安装为系统目录前需要配置redis环境变量
vim /etc/profile
# 新增
export REDIS_HOME=/opt/tools/redis5
export PATH=$PATH:$REDIS_HOME/bin
## 保存退出，重置配置文件
source /etc/profile
## 检查
echo $PATH
/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/root/bin:/opt/tools/redis5/bin:/opt/tools/redis5/bin
## 此时可以在任何目下执行 /opt/tools/redis5/bin 下的可执行文件
# 14. 进入到 Redis 根目录下 utils目录执行
./install_server.sh
## 此时会让你输入端口号，因为是第一次安装，默认使用6369，如后续还需安装再使用其他端口号
## 回车会自动在 etc 目录下生成对应的配置文件6379.conf
## 回车生成日志文件
## 回车生成数据存储目录，因为内存数据涉及到持久化
## 显示可执行程序的路径，如果没有显示就只能手动输入了/opt/tools/redis5/bin/redis-server
## 显示所有前面的配置信息，并创建启动脚本/etc/init.d/redis_6379
## 安装服务，添加到开机自启动，并启动redis服务
```

![1581859972981](F:\git\study\java\MD\image\1581859972981.png)

```
# 15. 查看安装完成的 redis 服务
service redis_6379 status
Redis is running (6559)
##########需要注意###############
如果外部网络访问，需要注释 bind 和 设置 protected-mode no
```

## Redis epoll

```
Redis 基于内存的单进程单线程K/V键值对的内存数据库。
Redis 是基于内存进行操作的，cup 不是Redis 的瓶颈，限制Redis瓶颈最有可能的就是机器的内存和网络带宽。正因为cpu不会成为Redis 的瓶颈，所以采用单线程方案。
为什么使用单线程Redis还能很快？
Redis i/o模型基于epoll实现，同步非阻塞多路复用I/O，首先epoll没有最大并发连接的限制，这个数远大于2048，这个数一般和系统内存关系很大。并且epoll只关注“活跃”的连接，因此在时间的网络环境中，epoll的效率就很高了。在client端获取数据时，没有了传统的文件拷贝，在kernel和用户进程上使用了“共享内存<epoll会在linux内核中申请一个简易的文件系统>”。共享内存有b+tree和一个双向链表，b+tree保存了非常多soket连接，epoll收集发生时间的连接，保存到链表中，在client端访问获取数据的时候，只需要向链表中添加和删除连接，并不会复制b+tree中的所有连接。有数据就返回
```

## 二进制安全

```
client从redis 获取数据，从socket中获取的是子节流，为了面向不同语言，如果采用系列化机制，不同语言序列化后的结果是不一样的。
Redis 在存储数据的，数据的变me是受客户端影响的。strlen key 查看
如：客户端编码为utf-8，存入字符（中），在redis中的长度为3个字节，客户端编码使用GBK存入字符（中），在redis中的长度为2。因此在使用redis的时候，各客户端要约定好使用相同编码格式对数据进行操作
```

## 数据类型

```shell
# 可以通过help 命令查看相关命令的相关属性，并且使用tab键提示
127.0.0.1:6380> help
redis-cli 5.0.7
To get help about Redis commands type:
## 查看所属组的命令属性；如：string，list..
      "help @<group>" to get a list of commands in <group>
      ## 具体命令的属性；如：help set
      ## set key value [expiration EX seconds|PX milliseconds] [NX|XX]
      ## set key value 有效时长【EX 秒|PX 毫秒】 NX表示没有key才可新增，XX表示存在key，才能修改
      "help <command>" for help on <command>
      "help <tab>" to get a list of possible help topics
      "quit" to exit
```

```
## 查看存入 key 时使用的命令所属类型组；如：string{set，strlen，incr，decr}list{lpush，lpop}...
type key
## 查看key对应value具体的类型，使用 append 后，为raw
object encoding key
```

```
对于string类型数据，从左往右索引从0开始，从右往左索引从-1开始
如：set key abcdefghijk。如果要取4到最后倒数第2个之间的字符，getrang key 4 -2

```

**二进制位的操作在计算机系统当中cpu计算是最快的**

```
bitmap 位图， 所属 string
从左往右，从零开始存储二进制（0或1）每8个位的长度表示一个字节；
应用场景：
1. 用户系统记录用户的登陆天数？
如果使用关系型数据，如mysql，存储一条数据，最少需要八个字节来存储当天是否登陆的信息，对于大的电商系统，当用户量非常大的时候，记录用户的登陆信息所消耗的磁盘空间是很大的。并且存储到关系型数据数据存到磁盘，要产生磁盘I/O，数据读取后还要对数据进行处理。
此时使用 Redis bitmap，我们按一年400天计算，每用户最多最多也就使用 400/8 =50 个字节，每一位上记录每天是否登陆
```

![1581950947739](F:\git\study\java\MD\image\1581950947739.png)

bitmap 位图旋转

![1581951927107](F:\git\study\java\MD\image\1581951927107.png)