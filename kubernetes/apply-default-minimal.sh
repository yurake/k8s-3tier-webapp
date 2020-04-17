#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

echo "### monitoring namespace"
cd ${ROOT_DIR}/monitoring
kubectl apply -f ./monitoring-namespace.yaml
echo "###"
echo ""

echo "### mysql"
cd ${ROOT_DIR}/mysql
kubectl apply -f ./mysql-pv.yaml
kubectl apply -f ./mysql-pvc.yaml
kubectl apply -f ./mysql-configmap.yaml
kubectl apply -f ./mysql-secret.yaml
kubectl apply -f ./mysql-deployment.yaml
kubectl apply -f ./mysql-service.yaml
echo "###"
echo ""

echo "### postgres"
cd ${ROOT_DIR}/postgres
kubectl apply -f ./postgres-pv.yaml
kubectl apply -f ./postgres-pvc.yaml
kubectl apply -f ./postgres-configmap.yaml
kubectl apply -f ./postgres-secret.yaml
kubectl apply -f ./postgres-deployment.yaml
kubectl apply -f ./postgres-service.yaml
echo "###"
echo ""

echo "### mongodb"
cd ${ROOT_DIR}/mongodb
kubectl apply -f ./mongodb-pv.yaml
kubectl apply -f ./mongodb-pvc.yaml
kubectl apply -f ./mongodb-configmap.yaml
kubectl apply -f ./mongodb-secret.yaml
kubectl apply -f ./mongodb-deployment.yaml
kubectl apply -f ./mongodb-service.yaml
echo "###"
echo ""

echo "### redis"
cd ${ROOT_DIR}/redis
kubectl apply -f ./redis-deployment.yaml
kubectl apply -f ./redis-service.yaml
echo "###"
echo ""

echo "### rabbitmq"
cd ${ROOT_DIR}/rabbitmq
kubectl apply -f ./rabbitmq-configmap.yaml
kubectl apply -f ./rabbitmq-secret.yaml
kubectl apply -f ./rabbitmq-deployment.yaml
kubectl apply -f ./rabbitmq-service.yaml
kubectl apply -f ./rabbitmq-ingress.yaml
echo "###"
echo ""

echo "### activemq"
cd ${ROOT_DIR}/activemq
kubectl apply -f ./activemq-deployment.yaml
kubectl apply -f ./activemq-service.yaml
kubectl apply -f ./activemq-ingress.yaml
echo "###"
echo ""

echo "### hazelcast"
cd ${ROOT_DIR}/hazelcast
kubectl apply -f ./hazelcast-statefulset.yaml
kubectl apply -f ./hazelcast-service.yaml
echo "###"
echo ""

echo "### memcached"
cd ${ROOT_DIR}/memcached
kubectl apply -f ./memcached-deployment.yaml
kubectl apply -f ./memcached-service.yaml
echo "###"
echo ""

echo "### nginx"
cd ${ROOT_DIR}/nginx
kubectl apply -f ./nginx-deployment.yaml
kubectl apply -f ./nginx-service.yaml
kubectl apply -f ./nginx-ingress.yaml
echo "###"
echo ""

echo "## jaeger"
cd ${ROOT_DIR}/jaeger
kubectl apply -f ./jaeger-all-in-one-template.yml
echo "###"
echo ""

echo "### jaxrs-activemq-quarkus"
cd ${ROOT_DIR}/jaxrs-activemq-quarkus
kubectl apply -f ./jaxrs-activemq-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-activemq-quarkus-service.yaml
kubectl apply -f ./jaxrs-activemq-quarkus-service-externalname.yaml
echo "###"
echo ""

echo "### jaxrs-hazelcast-quarkus"
cd ${ROOT_DIR}/jaxrs-hazelcast-quarkus
kubectl apply -f ./jaxrs-hazelcast-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-hazelcast-quarkus-service.yaml
kubectl apply -f ./jaxrs-hazelcast-quarkus-service-externalname.yaml
echo "###"
echo ""

echo "### jaxrs-memcached-quarkus"
cd ${ROOT_DIR}/jaxrs-memcached-quarkus
kubectl apply -f ./jaxrs-memcached-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-memcached-quarkus-service.yaml
kubectl apply -f ./jaxrs-memcached-quarkus-service-externalname.yaml
echo "###"
echo ""

echo "### jaxrs-mongodb-quarkus"
cd ${ROOT_DIR}/jaxrs-mongodb-quarkus
kubectl apply -f ./jaxrs-mongodb-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-mongodb-quarkus-service.yaml
kubectl apply -f ./jaxrs-mongodb-quarkus-service-externalname.yaml
echo "###"
echo ""

echo "### jaxrs-mysql-quarkus"
cd ${ROOT_DIR}/jaxrs-mysql-quarkus
kubectl apply -f ./jaxrs-mysql-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-mysql-quarkus-service.yaml
kubectl apply -f ./jaxrs-mysql-quarkus-service-externalname.yaml
echo "###"
echo ""

echo "### jaxrs-postgres-quarkus"
cd ${ROOT_DIR}/jaxrs-postgres-quarkus
kubectl apply -f ./jaxrs-postgres-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-postgres-quarkus-service.yaml
kubectl apply -f ./jaxrs-postgres-quarkus-service-externalname.yaml
echo "###"
echo ""

echo "### jaxrs-rabbitmq-quarkus"
cd ${ROOT_DIR}/jaxrs-rabbitmq-quarkus
kubectl apply -f ./jaxrs-rabbitmq-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-rabbitmq-quarkus-service.yaml
kubectl apply -f ./jaxrs-rabbitmq-quarkus-service-externalname.yaml
echo "###"
echo ""

echo "### jaxrs-redis-quarkus"
cd ${ROOT_DIR}/jaxrs-redis-quarkus
kubectl apply -f ./jaxrs-redis-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-redis-quarkus-service.yaml
kubectl apply -f ./jaxrs-redis-quarkus-service-externalname.yaml
echo "###"
echo ""
