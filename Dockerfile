#添加Java启动的必要镜像
FROM openjdk:17-jdk-slim
MAINTAINER lionel
ADD myapp.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]

