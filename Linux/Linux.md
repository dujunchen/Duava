# Linux

## 防火墙

CentOS 7.0默认使用的是firewall作为防火墙，CentOS6是使用iptables。

- CentOS6

  ```shell
  #查看防火墙的状态
  service iptable status
  #关闭防火墙
  #临时关闭防火墙
  servcie iptables stop
  #开启
  service iptables start
  #永久关闭防火墙
  chkconfig iptables off 
  #开启
  chkconfig iptables on
  #开放端口
  /sbin/iptables -I INPUT -p tcp --dport 80 -j ACCEPT
  #然后保存
  /etc/rc.d/init.d/iptables save
  #查看打开的端口
  /etc/init.d/iptables status
  ```

  

- CentOS 7

  ```shell
  #查看防火墙的状态
  firewall-cmd --state
  #或者
  systemctl list-unit-files|grep firewalld.service 
  #或者
  systemctl status firewalld.service
  #停止firewall
  systemctl stop firewalld.service 
  #禁止firewall开机启动
  systemctl disable firewalld.service 
  #在开机时启用
  systemctl enable firewalld.service
  systemctl start firewalld.service
  systemctl restart firewalld.service
  #查看已经开放的端口
  firewall-cmd --list-ports
  #开放端口
  firewall-cmd --zone=public --add-port=80/tcp --permanent
  ```

  

## 查看进程

- 根据进程名称查看进程

  ```shell
  ps -ef | grep <pname> | grep -v grep
  ```

- 根据端口查看进程

  ```shell
  #第一种
  lsof -i:<port>
  #第二种
  netstat -anp | grep <port>
  #获取进程号
  netstat -anp | grep <port> | awk '{print $7}' | cut -d/ -f1
  ```

- 根据PID查看端口

  ```shell
  #第一种
  netstat -anp | grep <PID>
  #第二种
  lsof -i -P | grep <PID>
  ```

## 文本处理

