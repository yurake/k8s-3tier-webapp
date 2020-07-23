FROM mysql:8.0
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF
COPY init /docker-entrypoint-initdb.d