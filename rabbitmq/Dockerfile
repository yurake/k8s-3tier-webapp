FROM rabbitmq:3.7.7-management
COPY custom_definitions.json /etc/rabbitmq/
RUN printf 'management.load_definitions = /etc/rabbitmq/custom_definitions.json\n' >> /etc/rabbitmq/rabbitmq.conf
