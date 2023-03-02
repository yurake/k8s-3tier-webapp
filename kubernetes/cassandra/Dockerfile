FROM cassandra:4.1
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
  org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"
USER cassandra
WORKDIR /opt/cassandra
COPY entrypoint-wrap.sh /
COPY init /docker-entrypoint-initdb.d
ENTRYPOINT ["/entrypoint-wrap.sh"]
CMD ["cassandra", "-f"]
