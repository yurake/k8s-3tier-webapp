#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

echo "## default"

echo "### mysql"
cd ${ROOT_DIR}/mysql
docker pull yurak/mysql:latest
docker build -t yurak/mysql:latest .
echo "###"
echo ""

echo "### postgres"
cd ${ROOT_DIR}/postgres
docker pull yurak/postgres:latest
docker build -t yurak/postgres:latest .
echo "###"
echo ""

echo "### redis"
echo "###"
echo ""

echo "### rabbitmq"
cd ${ROOT_DIR}/rabbitmq
docker pull yurak/rabbitmq:latest
docker build -t yurak/rabbitmq:latest .
echo "###"
echo ""

echo "### activemq"
echo "###"
echo ""

echo "### hazelcast"
cd ${ROOT_DIR}/hazelcast
docker pull yurak/hazelcast:latest
docker build -t yurak/hazelcast:latest .
echo "###"
echo ""

echo "### memcached"
echo "###"
echo ""

echo "### nginx"
cd ${ROOT_DIR}/nginx
docker pull yurak/nginx:latest
docker build -t yurak/nginx:latest .
echo "###"
echo ""

echo "### rabbitmq-mysql-quarkus"
cd ${ROOT_DIR}/../application/rabbitmq-mysql-quarkus
docker pull yurak/rabbitmq-mysql-quarkus:latest
docker build -t yurak/rabbitmq-mysql-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### wlp"
cd ${ROOT_DIR}/wlp
\cp -p ../../application/wlp-web-java-spring/target/spring.war .
docker pull yurak/wlp:latest
docker build -t yurak/wlp:latest .
echo "###"
echo ""

echo "### jaxrs-activemq-quarkus"
cd ${ROOT_DIR}/../application/jaxrs-activemq-quarkus
docker pull yurak/jaxrs-activemq-quarkus:latest
docker build -t yurak/jaxrs-activemq-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### jaxrs-hazelcast-quarkus"
cd ${ROOT_DIR}/../application/jaxrs-hazelcast-quarkus
docker pull yurak/jaxrs-hazelcast-quarkus:latest
docker build -t yurak/jaxrs-hazelcast-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### jaxrs-memcached-quarkus"
cd ${ROOT_DIR}/../application/jaxrs-memcached-quarkus
docker pull yurak/jaxrs-memcached-quarkus:latest
docker build -t yurak/jaxrs-memcached-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### jaxrs-activemq-quarkus"
cd ${ROOT_DIR}/../application/jaxrs-mysql-quarkus
docker pull yurak/jaxrs-mysql-quarkus:latest
docker build -t yurak/jaxrs-mysql-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### jaxrs-postgres-quarkus"
cd ${ROOT_DIR}/../application/jaxrs-postgres-quarkus
docker pull yurak/jaxrs-postgres-quarkus:latest
docker build -t yurak/jaxrs-postgres-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### jaxrs-rabbitmq-quarkus"
cd ${ROOT_DIR}/../application/jaxrs-rabbitmq-quarkus
docker pull yurak/jaxrs-rabbitmq-quarkus:latest
docker build -t yurak/jaxrs-rabbitmq-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### jaxrs-redis-quarkus"
cd ${ROOT_DIR}/../application/jaxrs-redis-quarkus
docker pull yurak/jaxrs-redis-quarkus:latest
docker build -t yurak/jaxrs-redis-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### redis-mysql-quarkus"
cd ${ROOT_DIR}/../application/redis-mysql-quarkus
docker pull yurak/redis-mysql-quarkus:latest
docker build -t yurak/redis-mysql-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### redis-mysql-helidon"
cd ${ROOT_DIR}/../application/redis-mysql-helidon
# docker build -t yurak/redis-mysql-helidon:latest .
echo "###"
echo ""

echo "### hazelcast-mysql-quarkus"
cd ${ROOT_DIR}/../application/hazelcast-mysql-quarkus
docker pull yurak/hazelcast-mysql-quarkus:latest
docker build -t yurak/hazelcast-mysql-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### activemq-mysql-quarkus"
cd ${ROOT_DIR}/../application/activemq-mysql-quarkus
docker pull yurak/activemq-mysql-quarkus:latest
docker build -t yurak/activemq-mysql-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### randompublish-quarkus"
cd ${ROOT_DIR}/../application/randompublish-quarkus
docker pull yurak/randompublish-quarkus:latest
docker build -t yurak/randompublish-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### scheduled-quarkus"
cd ${ROOT_DIR}/../application/scheduled-quarkus
docker pull yurak/scheduled-quarkus:latest
docker build -t yurak/scheduled-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "## jaeger"
echo "###"
echo ""

echo "## monitoring"

echo "### jenkins"
cd ${ROOT_DIR}/monitoring/jenkins
docker pull yurak/jenkins:latest
docker build -t yurak/jenkins:latest .
echo "###"
echo ""

echo "### node-exporter"
echo "###"
echo ""

echo "### kube-state-metrics"
echo "###"
echo ""

echo "### prometheus"
echo "###"
echo ""

echo "### grafana"
echo "###"
echo ""

echo "### jupyter"
echo "###"
echo ""

echo "### ab"
cd ${ROOT_DIR}/monitoring/test/ab
docker pull yurak/ab:latest
docker build -t yurak/ab:latest .
echo "###"
echo ""

echo "### postmannewman"
cd ${ROOT_DIR}/monitoring/test/postmannewman
docker pull yurak/postmannewman:latest
docker build -t yurak/postmannewman:latest .
echo "###"
echo ""

echo "### docker rmi tag:none"
docker image prune -f
echo "###"
echo ""
