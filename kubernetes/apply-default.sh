#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

./apply-default-minimal.sh

echo "### consumer-rabbitmq-quarkus"
cd ${ROOT_DIR}/consumer-rabbitmq-quarkus
kubectl apply -f ./consumer-rabbitmq-quarkus-deployment.yaml
echo "###"
echo ""

echo "### wlp"
cd ${ROOT_DIR}/wlp
kubectl apply -f ./wlp-configmap.yaml
kubectl apply -f ./wlp-deployment.yaml
kubectl apply -f ./wlp-service.yaml
kubectl apply -f ./wlp-ingress.yaml
echo "###"
echo ""

echo "### consumer-redis-quarkus"
cd ${ROOT_DIR}/consumer-redis-quarkus
kubectl apply -f ./consumer-redis-quarkus-deployment.yaml
echo "###"
echo ""

echo "### redis-mysql-helidon"
cd ${ROOT_DIR}/redis-mysql-helidon
kubectl apply -f ./redis-mysql-helidon-deployment.yaml
echo "###"
echo ""

echo "### consumer-hazelcast-quarkus"
cd ${ROOT_DIR}/consumer-hazelcast-quarkus
kubectl apply -f ./consumer-hazelcast-quarkus-deployment.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### consumer-activemq-quarkus"
cd ${ROOT_DIR}/consumer-activemq-quarkus
kubectl apply -f ./consumer-activemq-quarkus-deployment.yaml
echo "###"
echo ""

echo "### randompublish-quarkus"
cd ${ROOT_DIR}/randompublish-quarkus
kubectl apply -f ./randompublish-quarkus-deployment.yaml
kubectl apply -f ./randompublish-quarkus-service.yaml
echo "###"
echo ""

echo "### scheduled-quarkus"
cd ${ROOT_DIR}/scheduled-quarkus
kubectl apply -f ./scheduled-quarkus-deployment.yaml
echo "###"
echo ""
