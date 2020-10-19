FROM cassandra:4.0
COPY entrypoint-wrap.sh /
COPY init /docker-entrypoint-initdb.d
# ENTRYPOINT ["/entrypoint-wrap.sh"]
# CMD ["cassandra", "-f"]
