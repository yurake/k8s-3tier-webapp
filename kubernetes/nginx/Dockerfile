FROM nginx:1.19.1
ENV TZ Asia/Tokyo
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF
COPY default.conf /etc/nginx/conf.d/default.conf
COPY docs /etc/nginx/docs
