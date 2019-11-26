FROM java:8
MAINTAINER linwl
VOLUME /tmp
ENV TimeZone=Asia/Shanghai
COPY target/gateway.jar gateway.jar
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas
EXPOSE 9005
RUN ln -snf /usr/share/zoneinfo/$TimeZone /etc/localtime && echo $TimeZone > /etc/timezone
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=dev","gateway.jar"]