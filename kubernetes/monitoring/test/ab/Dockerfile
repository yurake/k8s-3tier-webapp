FROM alpine:3.12.1
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"
RUN apk --no-cache add apache2-utils=2.4.46-r1
WORKDIR /home
COPY sample.json /home/.
