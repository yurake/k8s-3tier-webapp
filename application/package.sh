#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

echo "### webapp-service"
cd webapp-service
./mvnw clean deploy
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jaxrs-quarkus"
cd jaxrs-quarkus
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
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### randompublish-quarkus"
cd randompublish-quarkus
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### scheduled-quarkus"
cd scheduled-quarkus
./mvnw clean package
cd ${ROOT_DIR}
echo "###"
echo ""
