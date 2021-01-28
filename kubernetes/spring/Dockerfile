FROM adoptopenjdk/openjdk11:alpine-slim
VOLUME /tmp
COPY spring.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
