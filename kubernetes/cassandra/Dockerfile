FROM cassandra:5.0@sha256:6f8f916e83c1e59934874588a35f15c81afa56da9d9c5a10862aa466085f8e29
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
