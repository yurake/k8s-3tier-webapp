FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD supersecret
ENV MYSQL_USER mysql
ENV MYSQL_PASSWORD mysql
ENV TZ Asia/Tokyo

RUN mkdir -p /var/log/mysql
RUN touch /var/log/mysql/mysql-bin.log
RUN chown -R mysql:mysql /var/log/mysql

COPY master.cnf /etc/mysql/conf.d/my.cnf
COPY init /docker-entrypoint-initdb.d
