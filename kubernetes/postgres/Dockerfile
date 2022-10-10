FROM postgres:14.5-alpine
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"
COPY init /docker-entrypoint-initdb.d
COPY conf/postgresql.conf /etc/postgresql/postgresql.conf
CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]
