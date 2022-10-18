#!/bin/sh
set -eu

ROOT_DIR="$(
	cd "$(dirname -- "$0")"
	cd ..
	pwd
)"

echo "### zookeeper"
cd "$ROOT_DIR"/zookeeper
kubectl apply -f ./zookeeper-statefulset.yaml
kubectl apply -f ./zookeeper-service.yaml
echo "###"
echo ""

echo "### kafka"
cd "$ROOT_DIR"/kafka
kubectl apply -f ./kafka-statefulset.yaml
kubectl apply -f ./kafka-service.yaml
echo "###"
echo ""

echo "### producer-kafka-quarkus"
cd "$ROOT_DIR"/producer-kafka-quarkus
kubectl apply -f ./producer-kafka-quarkus-deployment.yaml
echo "###"
echo ""

echo "### jaxrs-kafka-quarkus"
cd "$ROOT_DIR"/jaxrs-kafka-quarkus
kubectl apply -f ./jaxrs-kafka-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-kafka-quarkus-service.yaml
kubectl apply -f ./jaxrs-kafka-quarkus-service-externalname.yaml
echo "###"
echo ""

echo "### converter-kafka-quarkus"
cd "$ROOT_DIR"/converter-kafka-quarkus
kubectl apply -f ./converter-kafka-quarkus-deployment.yaml
echo "###"
echo ""

echo "### consumer-kafka-quarkus"
cd "$ROOT_DIR"/consumer-kafka-quarkus
kubectl apply -f ./consumer-kafka-quarkus-deployment.yaml
echo "###"
echo ""

echo "### consumer-rabbitmq-quarkus"
cd "$ROOT_DIR"/consumer-rabbitmq-quarkus
kubectl apply -f ./consumer-rabbitmq-quarkus-deployment.yaml
echo "###"
echo ""

echo "### consumer-redis-quarkus"
cd "$ROOT_DIR"/consumer-redis-quarkus
kubectl apply -f ./consumer-redis-quarkus-deployment.yaml
echo "###"
echo ""

echo "### consumer-hazelcast-quarkus"
cd "$ROOT_DIR"/consumer-hazelcast-quarkus
kubectl apply -f ./consumer-hazelcast-quarkus-deployment.yaml
echo "###"
echo ""

echo "### consumer-activemq-quarkus"
cd "$ROOT_DIR"/consumer-activemq-quarkus
kubectl apply -f ./consumer-activemq-quarkus-deployment.yaml
echo "###"
echo ""

echo "### randompublish-quarkus"
cd "$ROOT_DIR"/randompublish-quarkus
kubectl apply -f ./randompublish-quarkus-deployment.yaml
kubectl apply -f ./randompublish-quarkus-service.yaml
echo "###"
echo ""

echo "### scheduled-quarkus"
cd "$ROOT_DIR"/scheduled-quarkus
kubectl apply -f ./scheduled-quarkus-deployment.yaml
echo "###"
echo ""
