FROM openliberty/open-liberty:21.0.0.1-full-java11-openj9-ubi
COPY lib/ /config/resources/
COPY --chown=1001:0 server.xml /config/
COPY --chown=1001:0 jvm.options /config/
COPY --chown=1001:0 openliberty.war /config/dropins/
RUN configure.sh
