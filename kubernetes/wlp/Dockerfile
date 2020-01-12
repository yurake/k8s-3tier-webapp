FROM websphere-liberty:19.0.0.6-webProfile8
COPY lib/mysql/mysql-connector-java-8.0.12.jar /config/resources/mysql/
COPY lib/postgres/postgresql-42.2.9.jar /config/resources/postgres/
COPY lib/activemq/activemq-rar-5.15.11.rar /config/resources/activemq/
COPY repositories.properties /opt/ibm/wlp/etc/
COPY wlp-featureRepo-19.0.0.6.zip /opt/ibm/wlp/usr/shared/resources/
COPY --chown=1001:0  server.xml /config/
COPY --chown=1001:0  spring.war /config/dropins/
RUN installUtility install --acceptLicense defaultServer
ENV LICENSE accept
