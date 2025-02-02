FROM postgres:17.2-alpine@sha256:0bcc5bbbb2aa9c9b4c6505845918c7eb55d783cf5c1f434fac33012579fb149d
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"
USER postgres
COPY init /docker-entrypoint-initdb.d
COPY conf/postgresql.conf /etc/postgresql/postgresql.conf
CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]
