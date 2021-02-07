FROM postman/newman:5.2.2-alpine
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"
COPY hostname-quarkus.postman_environment.json /etc/newman/
COPY jaxrs-quarkus.postman_collection.json /etc/newman/
