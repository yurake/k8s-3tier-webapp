FROM postgres:12.1-alpine
COPY init /docker-entrypoint-initdb.d
COPY conf/postgresql.conf /etc/postgresql/postgresql.conf
CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]
