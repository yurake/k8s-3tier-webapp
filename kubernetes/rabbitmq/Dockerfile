FROM rabbitmq:3.8.2-management-alpine
RUN apk update && \
    apk --no-cache add tzdata && \
    cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    apk del tzdata
COPY custom_definitions.json /etc/rabbitmq/
RUN printf 'management.load_definitions = /etc/rabbitmq/custom_definitions.json\n' >> /etc/rabbitmq/rabbitmq.conf
RUN rabbitmq-plugins enable rabbitmq_prometheus
