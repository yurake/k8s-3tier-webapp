#!/bin/sh
set -eu

ROOT_DIR="$(
  cd "$(dirname -- "$0")"
  cd ..
  pwd
)"

usage() {
  cat <<EOF

Script:
    Apply minimal service to Minikube

Usage:
    "$(basename "$0")" "[<options>]"

Options:
    crc     Apply to Code Rady Container
EOF
}

if [ $# -gt 1 ]; then
  echo "too many arguments"
  usage
  exit 1
elif [ $# -eq 1 ] && [ "$1" != "crc" ]; then
  echo "incorrect argument: $1"
  usage
  exit 1
elif [ $# -eq 1 ] && [ "$1" = "crc" ]; then
  readonly is_crc=true
else
  readonly is_crc=false
fi

cd "$ROOT_DIR"/bin
./apply-monitoring-namespace.sh

echo "### mysql"
cd "$ROOT_DIR"/mysql
kubectl apply -f ./mysql-pv.yaml
if "${is_crc}"; then
  kubectl apply -f ./mysql-pvc-crc.yaml
else
  kubectl apply -f ./mysql-pvc.yaml
fi
kubectl apply -f ./mysql-configmap.yaml
kubectl apply -f ./mysql-secret.yaml
kubectl apply -f ./mysql-deployment.yaml
kubectl apply -f ./mysql-service.yaml
echo "###"
echo ""

echo "### postgres"
cd "$ROOT_DIR"/postgres
kubectl apply -f ./postgres-pv.yaml
if "${is_crc}"; then
  kubectl apply -f ./postgres-pvc-crc.yaml
else
  kubectl apply -f ./postgres-pvc.yaml
fi
kubectl apply -f ./postgres-configmap.yaml
kubectl apply -f ./postgres-secret.yaml
kubectl apply -f ./postgres-deployment.yaml
kubectl apply -f ./postgres-service.yaml
echo "###"
echo ""

echo "### redis"
cd "$ROOT_DIR"/redis
kubectl apply -f ./redis-deployment.yaml
kubectl apply -f ./redis-service.yaml
echo "###"
echo ""

echo "### rabbitmq"
cd "$ROOT_DIR"/rabbitmq
kubectl apply -f ./rabbitmq-configmap.yaml
kubectl apply -f ./rabbitmq-secret.yaml
kubectl apply -f ./rabbitmq-deployment.yaml
kubectl apply -f ./rabbitmq-service.yaml
kubectl apply -f ./rabbitmq-ingress.yaml
echo "###"
echo ""

echo "### activemq"
cd "$ROOT_DIR"/activemq
kubectl apply -f ./activemq-pv.yaml
if "${is_crc}"; then
  kubectl apply -f ./activemq-pvc-crc.yaml
else
  kubectl apply -f ./activemq-pvc.yaml
fi
kubectl apply -f ./activemq-deployment.yaml
kubectl apply -f ./activemq-service.yaml
kubectl apply -f ./activemq-ingress.yaml
echo "###"
echo ""

echo "### hazelcast"
cd "$ROOT_DIR"/hazelcast
kubectl apply -f ./hazelcast-configmap.yaml
kubectl apply -f ./hazelcast-statefulset.yaml
kubectl apply -f ./hazelcast-service.yaml
echo "###"
echo ""

echo "### memcached"
cd "$ROOT_DIR"/memcached
kubectl apply -f ./memcached-deployment.yaml
kubectl apply -f ./memcached-service.yaml
echo "###"
echo ""

echo "### openliberty"
cd "$ROOT_DIR"/openliberty
kubectl apply -f ./openliberty-configmap.yaml
kubectl apply -f ./openliberty-secret.yaml
kubectl apply -f ./openliberty-deployment.yaml
kubectl apply -f ./openliberty-service.yaml
kubectl apply -f ./openliberty-service-externalname.yaml
kubectl apply -f ./openliberty-ingress.yaml
echo "###"
echo ""
