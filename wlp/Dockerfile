FROM websphere-liberty:webProfile8
COPY --chown=1001:0  spring.war /config/dropins/
COPY --chown=1001:0  server.xml /config/
COPY lib/mysql/mysql-connector-java-8.0.12.jar /config/resources/mysql/
ENV LICENSE accept
