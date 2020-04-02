# e-school-service-account
spring boot 2.2.2  
spring cloud config client  
spring cloud eureka client  

## 1 脚本说明
deploy-image-maven.sh : 通过docker命令构建docker镜像
deploy-image-docker.sh : 通过maven插件构建&上传docker镜像
application-start.sh : 启动本服务

## 2 配置说明
1. bootstrap.yml 系统级配置 不会被覆盖
2. application.properties 各中间件属性配置 不要去覆盖
3. application.yml 各中间件连接配置 区分环境