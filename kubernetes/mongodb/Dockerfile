FROM mongo:4.2-bionic
ARG BUILD_DATE
ARG VCS_REFmongodb
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF
COPY init /docker-entrypoint-initdb.d
