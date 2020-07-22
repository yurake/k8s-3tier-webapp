FROM jenkins/jenkins:2.248-alpine
USER root

# update apk repositories
RUN echo "http://dl-2.alpinelinux.org/alpine/edge/main" > /etc/apk/repositories
RUN echo "http://dl-2.alpinelinux.org/alpine/edge/community" >> /etc/apk/repositories
RUN echo "http://dl-2.alpinelinux.org/alpine/edge/testing" >> /etc/apk/repositories

# install docker
RUN apk -U --no-cache --allow-untrusted add gcc=9.3.0-r4 docker=19.03.12-r0

# install kubectl
RUN curl -LO https://storage.googleapis.com/kubernetes-release/release/"$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)"/bin/linux/amd64/kubectl; chmod +x ./kubectl; mv ./kubectl /usr/local/bin/kubectl

# Setting the number of executors
COPY init/executors.groovy /usr/share/jenkins/ref/init.groovy.d/executors.groovy

# change timezone
RUN apk update && \
    apk --no-cache add tzdata=2020a-r0 && \
    cp /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    apk del tzdata
ENV JAVA_OPTS -Duser.timezone=Asia/Tokyo

# Install default plugins
COPY init/plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

# Install default jobs
COPY init/jobs /usr/share/jenkins/ref/jobs

USER jenkins
