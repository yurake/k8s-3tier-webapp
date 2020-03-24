#!/bin/sh
set -eu

eval $(minikube docker-env)
ROOT_DIR=$(cd $(dirname -- $0); pwd)

echo "## default"

echo "### mysql"
cd ${ROOT_DIR}/mysql
docker build -t yurak/mysql:latest .
echo "###"
echo ""

echo "### postgres"
cd ${ROOT_DIR}/postgres
docker build -t yurak/postgres:latest .
echo "###"
echo ""

echo "### redis"
echo "###"
echo ""

echo "### rabbitmq"
cd ${ROOT_DIR}/rabbitmq
docker build -t yurak/rabbitmq:latest .
echo "###"
echo ""

echo "### activemq"
echo "###"
echo ""

echo "### hazelcast"
cd ${ROOT_DIR}/hazelcast
docker build -t yurak/hazelcast:latest .
echo "###"
echo ""

echo "### memcached"
echo "###"
echo ""

echo "### nginx"
cd ${ROOT_DIR}/nginx
docker build -t yurak/nginx:latest .
echo "###"
echo ""

echo "### rabbitmq-mysql-quarkus"
cd ${ROOT_DIR}/../application/rabbitmq-mysql-quarkus
docker build -t yurak/rabbitmq-mysql-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### wlp"
cd ${ROOT_DIR}/wlp
\cp -p ../../application/wlp-web-java-spring/target/spring.war .
docker build -t yurak/wlp:latest .
echo "###"
echo ""

echo "### jaxrs-quarkus"
cd ${ROOT_DIR}/../application/jaxrs-quarkus
docker build -t yurak/jaxrs-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### redis-mysql-quarkus"
cd ${ROOT_DIR}/../application/redis-mysql-quarkus
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
docker build -t yurak/hazelcast-mysql-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### activemq-mysql-quarkus"
cd ${ROOT_DIR}/../application/activemq-mysql-quarkus
docker build -t yurak/activemq-mysql-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### randompublish-quarkus"
cd ${ROOT_DIR}/../application/randompublish-quarkus
docker build -t yurak/randompublish-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "### scheduled-quarkus"
cd ${ROOT_DIR}/../application/scheduled-quarkus
docker build -t yurak/scheduled-quarkus:latest -f src/main/docker/Dockerfile.jvm .
echo "###"
echo ""

echo "## jaeger"
echo "###"
echo ""

echo "## monitoring"

echo "### jenkins"
cd ${ROOT_DIR}/monitoring/jenkins
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
docker build -t yurak/ab:latest .
echo "###"
echo ""

echo "### postmannewman"
cd ${ROOT_DIR}/monitoring/test/postmannewman
docker build -t yurak/postmannewman:latest .
echo "###"
echo ""

echo "### docker rmi tag:none"
docker image prune -f
echo "###"
echo ""
