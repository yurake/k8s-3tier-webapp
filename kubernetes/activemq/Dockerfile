FROM vromero/activemq-artemis:2.11.0-alpine
USER root
RUN apk update && \
    apk --no-cache add tzdata && \
    cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    apk del tzdata
