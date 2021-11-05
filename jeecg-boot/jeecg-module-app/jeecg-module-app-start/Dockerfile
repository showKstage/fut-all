FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER chengjuzhen@163.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /jeecg-cloud-app

WORKDIR /jeecg-cloud-app

EXPOSE 7002

ADD ./target/*.jar ./

CMD sleep 30;java -Djava.security.egd=file:/dev/./urandom -jar *.jar
