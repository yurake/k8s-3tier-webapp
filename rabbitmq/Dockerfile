FROM rabbitmq:3.7.7-management

ENV RABBITMQ_DEFAULT_VHOST vhost1
ENV RABBITMQ_DEFAULT_USER rabbitmq
ENV RABBITMQ_DEFAULT_PASS rabbitmq

RUN printf 'management.load_definitions = /etc/rabbitmq/custom_definitions.json\n' >> /etc/rabbitmq/rabbitmq.conf
