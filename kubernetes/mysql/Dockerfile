FROM mysql:8.0
COPY init /docker-entrypoint-initdb.d
CMD ["--default-authentication-plugin=mysql_native_password"]