# linux command

### 查看函数和指令

```
##  https://blog.csdn.net/Wyx_wx/article/details/79507823
yum install man man-pages
```

### 系统软重启

```
shutdown -r now
```



## vim

### 多行注释

vim  任意文件

按 **ctrl+v**， 在文件头按 **home**键

可以按 **j**或**k**上下移动，或者上下键移动选择需要注释的行（shift+g 选择全文）

按大写**I**进入insert模式， 

输入**#**，按 **esc**回到命令模式。此时前面几行就全部注释

### 显示行号

在命令模式输入 **set nu**

### 查找字符

在命令模式输入 **/**要查到的字符 

输入 **n** 查找下一个 **N** 查找上一个

### 第一行

命令模式双击 **gg**

### 最后一行

shift+g

## Redis

### 查看实例信息

```shell
/usr/local/redis/src/redis-cli -h ip(bind ip) -p 6379 -a password info replication
```

#### 客户端访问

```shell
/usr/local/redis/src/redis-cli -h （如果为本机使用内网ip，如果为同一云服务不同主机使用外网ip） -p port
```

