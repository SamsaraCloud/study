# Mysql Install

[安装](https://www.cnblogs.com/duanrantao/p/8988116.html)

mysql 启动报错

```shell
[root@instance-i2b79zqq mysql]# service mysql start
Starting MySQL.Logging to '/usr/local/mysql/data/instance-i2b79zqq.err'.
.The server quit without updating PID file (/usr/local/mysq[FAILED]nstance-i2b79zqq.pid).

```

```shell
cd /usr/local/mysql/
chown -R mysql .
chgrp -R mysql .
/usr/local/mysql/scripts/mysql_install_db --user=mysql
# 重新执行 
service mysql start
```

