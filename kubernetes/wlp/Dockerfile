FROM openliberty/open-liberty:20.0.0.8-kernel-java8-openj9-ubi
COPY ./lib/ /config/resources/
COPY ./extension/ /opt/ol/wlp/usr/extension/
COPY --chown=1001:0  ./server.xml /config/
COPY --chown=1001:0  ./spring.war /config/dropins/
RUN configure.sh
