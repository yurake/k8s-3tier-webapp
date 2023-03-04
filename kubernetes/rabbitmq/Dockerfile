FROM rabbitmq:3.11.10-management-alpine
ARG BUILD_DATE
ARG VCS_REF

# ENV TZDATA_VERSION="2022f-r1"

LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"
RUN apk update && \
    # apk --no-cache add tzdata=${TZDATA_VERSION} && \
    apk --no-cache add tzdata && \
    cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    apk del tzdata
USER rabbitmq

COPY custom_definitions.json /etc/rabbitmq/
RUN printf 'management.load_definitions = /etc/rabbitmq/custom_definitions.json\n' >> /etc/rabbitmq/rabbitmq.conf && \
    rabbitmq-plugins enable rabbitmq_prometheus
