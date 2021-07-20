FROM opentracing/nginx-opentracing:0.19.0
ENV TZ Asia/Tokyo
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"
COPY default.conf /etc/nginx/conf.d/default.conf
COPY jaeger-nginx-config.json /etc/jaeger-nginx-config.json
COPY docs /etc/nginx/docs
CMD ["nginx", "-g", "daemon off; load_module /etc/nginx/modules/ngx_http_opentracing_module.so;"]
