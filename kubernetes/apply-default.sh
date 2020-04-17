#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

./apply-default-minimal.sh

echo "### rabbitmq-mysql-quarkus"
cd ${ROOT_DIR}/rabbitmq-mysql-quarkus
kubectl apply -f ./rabbitmq-mysql-quarkus-deployment.yaml
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

echo "### redis-mysql-quarkus"
cd ${ROOT_DIR}/redis-mysql-quarkus
kubectl apply -f ./redis-mysql-quarkus-deployment.yaml
echo "###"
echo ""

echo "### redis-mysql-helidon"
cd ${ROOT_DIR}/redis-mysql-helidon
kubectl apply -f ./redis-mysql-helidon-deployment.yaml
echo "###"
echo ""

echo "### hazelcast-mysql-quarkus"
cd ${ROOT_DIR}/hazelcast-mysql-quarkus
kubectl apply -f ./hazelcast-mysql-quarkus-deployment.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### activemq-mysql-quarkus"
cd ${ROOT_DIR}/activemq-mysql-quarkus
kubectl apply -f ./activemq-mysql-quarkus-deployment.yaml
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
