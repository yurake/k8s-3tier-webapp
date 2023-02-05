FROM alpine:3.17
ARG BUILD_DATE
ARG VCS_REF

# ENV APACHE2_UTILS_VERSION="2.4.54-r0"

LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"

RUN addgroup -S apatchbench && adduser -S apatchbench -G apatchbench
USER apatchbench

# RUN apk --no-cache add apache2-utils=${APACHE2_UTILS_VERSION}
RUN apk --no-cache add apache2-utils
WORKDIR /home
COPY sample.json /home/.
