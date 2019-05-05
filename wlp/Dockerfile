FROM websphere-liberty:webProfile8
COPY --chown=1001:0  web.war /config/dropins/
COPY --chown=1001:0  server.xml /config/
ENV LICENSE accept
