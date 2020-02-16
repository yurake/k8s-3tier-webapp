FROM openliberty/open-liberty:19.0.0.12-kernel-java8-openj9-ubi
COPY ./lib/mysql/mysql-connector-java-8.0.12.jar /config/resources/mysql/
COPY ./lib/postgres/postgresql-42.2.9.jar /config/resources/postgres/
COPY ./lib/activemq/activemq-rar-5.15.11.rar /config/resources/activemq/
COPY ./extension/ /opt/ol/wlp/usr/extension/
COPY --chown=1001:0  ./server.xml /config/
COPY --chown=1001:0  ./spring.war /config/dropins/
RUN configure.sh
