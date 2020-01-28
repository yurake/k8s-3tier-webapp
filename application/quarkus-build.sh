#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

echo "### webapp-service"
cd webapp-service
./mvnw clean
./mvnw package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jaxrs-quarkus"
cd jaxrs-quarkus
./mvnw clean
./mvnw package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### redis-mysql-quarkus"
cd redis-mysql-quarkus
./mvnw clean
./mvnw package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### hazelcast-mysql-quarkus"
cd hazelcast-mysql-quarkus
./mvnw clean
./mvnw package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### activemq-mysql-quarkus"
cd activemq-mysql-quarkus
./mvnw clean
./mvnw package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### randompublish-quarkus"
cd randompublish-quarkus
./mvnw clean
./mvnw package
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### scheduled-quarkus"
cd scheduled-quarkus
./mvnw clean
./mvnw package
cd ${ROOT_DIR}
echo "###"
echo ""
