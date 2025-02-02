FROM mongo:4.4-bionic@sha256:3d0e6df9fd5bc42cbf8ef8bc9e6c4e78f6f26c7157dbd7bdec72d202ab8ebe3a
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"
USER mongodb
COPY init /docker-entrypoint-initdb.d
