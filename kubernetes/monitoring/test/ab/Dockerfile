FROM alpine:3.11.3
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF
RUN apk --no-cache add apache2-utils=2.4.43-r0
WORKDIR /home
COPY sample.json /home/.
