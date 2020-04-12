### 安装

安装依赖

pcre 

```
## 进入到解压目录
## configure: error: You need a C++ compiler for C++ support
./configura 
## 安装gcc再次执行./configura
yum install -y gcc gcc-c++
## 编译并安装
make && make install
## 查看安装版本
pcre-config --version
```

安装其他依赖

```
yum -y install make zlib zlib-devel gcc-c++ libtool openssl openssl-devel
```

安装nginx

```
## 解压压缩包
## 进入到解压目录
./configura
## 编译安装
make && make install
## 安装完在 /usr/local/nginx/sbin 目录下有启动脚本
## 启动nginx
./nginx
## 默认监听端口 80
```

```
## 开放端口
sudo firewall-cmd --add-port=8000/tcp --perment
## 重启防火墙
firewall-cmd --reload
```

### Nginx 常用命令

```
## 关闭
nginx -s stop
## 启动
nginx
## 重新加载 nginx安装下的配置文件 nginx.conf
nginx -s reload
```

### Nginx 配置文件

```

```

























