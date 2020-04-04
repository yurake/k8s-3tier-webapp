#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

echo "### webapp-service"
cd webapp-service
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jaxrs-activemq-quarkus"
cd jaxrs-activemq-quarkus
./mvnw clean package -Pnative
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jaxrs-hazelcast-quarkus"
cd jaxrs-hazelcast-quarkus
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jaxrs-memcached-quarkus"
cd jaxrs-memcached-quarkus
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jaxrs-mysql-quarkus"
cd jaxrs-mysql-quarkus
./mvnw clean package -Pnative
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jaxrs-postgres-quarkus"
cd jaxrs-postgres-quarkus
./mvnw clean package -Pnative
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jaxrs-rabbitmq-quarkus"
cd jaxrs-rabbitmq-quarkus
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jaxrs-redis-quarkus"
cd jaxrs-redis-quarkus
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### rabbitmq-mysql-quarkus"
cd rabbitmq-mysql-quarkus
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### redis-mysql-quarkus"
cd redis-mysql-quarkus
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### hazelcast-mysql-quarkus"
cd hazelcast-mysql-quarkus
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### activemq-mysql-quarkus"
cd activemq-mysql-quarkus
./mvnw clean package -Pnative
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### randompublish-quarkus"
cd randompublish-quarkus
./mvnw clean package -Pnative
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### scheduled-quarkus"
cd scheduled-quarkus
./mvnw clean package -Pnative
cd ${ROOT_DIR}
echo "###"
echo ""
