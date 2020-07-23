FROM postman/newman:4.6-alpine
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF
COPY hostname.postman_environment.json /etc/newman/
COPY jaxrs-quarkus.postman_collection.json /etc/newman/
