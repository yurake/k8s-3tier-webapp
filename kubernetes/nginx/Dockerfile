FROM nginx:1.19.9
ENV TZ Asia/Tokyo
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"
COPY default.conf /etc/nginx/conf.d/default.conf
COPY docs /etc/nginx/docs
