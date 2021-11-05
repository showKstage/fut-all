FROM nginx
MAINTAINER changcheng.shao
ENV LANG en_US.UTF-8
RUN mkdir  -p  /var/www \
    &&  mkdir -p /var/www/html

ADD dist/ /var/www/html/
ADD default.conf /etc/nginx/conf.d/
EXPOSE 80
EXPOSE 443