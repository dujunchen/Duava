# Docker

## 基本概念

- 镜像
- 容器
- 仓库

![image-20200722145058832](Docker.assets/image-20200722145058832.png)

## 常用命令

## Dockerfile

### RUN、CMD、ENTRYPOINT的区别

- RUN用来执行docker build过程中需要执行的命令，可以有多条，每一个RUN都会创建新的一层镜像层，因此为了减少镜像的体积，尽量将多条需要RUN的命令用&&合并成一条

- CMD用来执行容器启动时默认执行的命令，但是可以被docker run后面指定的命令参数替换，并且不支持接收docker run的参数。CMD只能有一条，如果写了多条，最后一条才会生效

- ENTRYPOINT和CMD相似，只是ENTRYPOINT可以接收docker run的参数

### COPY、ADD的区别

- COPY是将宿主机的文件复制进容器，如果是压缩文件不会自动解压

- ADD和COPY类似，只是ADD会自动解压压缩文件，同时ADD还能指定URL远程文件的添加

## 数据卷

## Docker网络

## Docker Compose

## Docker Swarm