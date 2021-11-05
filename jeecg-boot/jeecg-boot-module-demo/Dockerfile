FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER jeecgos@163.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /jeecg-cloud-demo

WORKDIR /jeecg-cloud-demo

EXPOSE 7003

ADD ./target/*.jar ./

CMD sleep 15;java -Djava.security.egd=file:/dev/./urandom -jar *.jar
