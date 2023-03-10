FROM jenkins/jenkins:2.394-alpine
ARG BUILD_DATE
ARG VCS_REF

# ENV TZDATA_VERSION="2022f-r1"
# ENV GCC_VERSION="11.2.1_git20220219-r2"
# ENV DOCKER_VERSION="20.10.20-r0"
# ENV PYTHON3_VERSION="3.10.8-r0"
# ENV GUNPLOT_VERSION="5.4.3-r0"

LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"
USER root

# update apk repositories
# RUN echo "http://dl-2.alpinelinux.org/alpine/edge/main" > /etc/apk/repositories
# RUN echo "http://dl-2.alpinelinux.org/alpine/edge/community" >> /etc/apk/repositories
# RUN echo "http://dl-2.alpinelinux.org/alpine/edge/testing" >> /etc/apk/repositories

# install docker
# RUN apk -U --no-cache --allow-untrusted add gcc=${GCC_VERSION} docker=${DOCKER_VERSION} python3=${PYTHON3_VERSION} gnuplot=${GUNPLOT_VERSION}
RUN apk -U --no-cache --allow-untrusted add gcc docker python3 gnuplot

# install kubectl
RUN curl -LO https://storage.googleapis.com/kubernetes-release/release/"$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)"/bin/linux/amd64/kubectl; chmod +x ./kubectl; mv ./kubectl /usr/local/bin/kubectl

# Setting the number of executors
COPY init/executors.groovy /usr/share/jenkins/ref/init.groovy.d/executors.groovy

# change timezone
RUN apk update && \
    # apk --no-cache add tzdata=${TZDATA_VERSION} && \
    apk --no-cache add tzdata && \
    cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    apk del tzdata
ENV JAVA_OPTS -Duser.timezone=Asia/Tokyo

# Install default plugins
COPY init/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Install default jobs
COPY init/jobs /usr/share/jenkins/ref/jobs

USER jenkins
