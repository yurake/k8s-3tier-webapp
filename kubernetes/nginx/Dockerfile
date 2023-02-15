FROM nginx:1.23.3
ENV TZ Asia/Tokyo
ARG BUILD_DATE
ARG VCS_REF
LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"

COPY default.conf /etc/nginx/conf.d/default.conf
COPY docs /etc/nginx/docs

# Implement changes required to run NGINX as an unprivileged user
RUN sed -i '/user  nginx;/d' /etc/nginx/nginx.conf \
    && sed -i 's,/var/run/nginx.pid,/tmp/nginx.pid,' /etc/nginx/nginx.conf \
    && sed -i "/^http {/a \    proxy_temp_path /tmp/proxy_temp;\n \
    client_body_temp_path /tmp/client_temp;\n    fastcgi_temp_path /tmp/fastcgi_temp;\n \
    uwsgi_temp_path /tmp/uwsgi_temp;\n    scgi_temp_path /tmp/scgi_temp;\n" /etc/nginx/nginx.conf \
    # Nginx user must own the cache and etc directory to write cache and tweak the nginx config
    && chown -R nginx:nginx /var/cache/nginx \
    && chmod -R g+w /var/cache/nginx \
    && chown -R nginx:nginx /etc/nginx \
    && chmod -R g+w /etc/nginx

EXPOSE 8080
USER nginx
