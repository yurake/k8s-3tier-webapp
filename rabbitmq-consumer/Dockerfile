FROM openjdk:8-alpine

RUN mkdir -p /target/lib
COPY lib /target/lib/
COPY rabbitmq-consumer.jar /target/lib/
ENV CLASSPATH /target/lib/*

CMD java batch.TaskServiceMain
