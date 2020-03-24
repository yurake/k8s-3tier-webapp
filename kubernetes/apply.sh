#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

echo "## default"

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

echo "### jaxrs-quarkus"
cd ${ROOT_DIR}/jaxrs-quarkus
kubectl apply -f ./jaxrs-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-quarkus-service.yaml
kubectl apply -f ./jaxrs-quarkus-service-externalname.yaml
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

echo "## jaeger"
cd ${ROOT_DIR}/jaeger
kubectl apply -f ./jaeger-all-in-one-template.yml
echo "###"
echo ""

echo "## monitoring"
cd ${ROOT_DIR}/monitoring
kubectl apply -f ./monitoring-namespace.yaml
echo "###"
echo ""

echo "### jenkins"
cd ${ROOT_DIR}/monitoring/jenkins
kubectl apply -f ./jenkins-rbac.yaml
kubectl apply -f ./jenkins-pv.yaml
kubectl apply -f ./jenkins-pvc.yaml
kubectl apply -f ./jenkins-deployment.yaml
kubectl apply -f ./jenkins-service.yaml
kubectl apply -f ./jenkins-ingress.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### node-exporter"
cd ${ROOT_DIR}/monitoring/node-exporter
kubectl apply -f ./node-exporter-pv-sys.yaml
kubectl apply -f ./node-exporter-pvc-sys.yaml
kubectl apply -f ./node-exporter-pv-proc.yaml
kubectl apply -f ./node-exporter-pvc-proc.yaml
kubectl apply -f ./node-exporter-daemonset.yaml
kubectl apply -f ./node-exporter-service.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### kube-state-metrics"
cd ${ROOT_DIR}/monitoring/kube-state-metrics
kubectl apply -f ./kube-state-metrics-rbac.yaml
kubectl apply -f ./kube-state-metrics-deployment.yaml
kubectl apply -f ./kube-state-metrics-service.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### prometheus"
cd ${ROOT_DIR}/monitoring/prometheus
kubectl apply -f ./prometheus-rbac.yaml
kubectl apply -f ./prometheus-pv.yaml
kubectl apply -f ./prometheus-configmap.yaml
kubectl apply -f ./prometheus-statefulset.yaml
kubectl apply -f ./prometheus-service.yaml
kubectl apply -f ./prometheus-ingress.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### grafana"
cd ${ROOT_DIR}/monitoring/grafana
kubectl apply -f ./grafana-pv.yaml
kubectl apply -f ./grafana-pvc.yaml
kubectl apply -f ./grafana-deployment.yaml
kubectl apply -f ./grafana-service.yaml
kubectl apply -f ./grafana-ingress.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jupyter"
cd ${ROOT_DIR}/monitoring/jupyter
kubectl apply -f ./jupyter-pv.yaml
kubectl apply -f ./jupyter-pvc.yaml
kubectl apply -f ./jupyter-configmap.yaml
kubectl apply -f ./jupyter-deployment.yaml
kubectl apply -f ./jupyter-service.yaml
kubectl apply -f ./jupyter-ingress.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### ab"
echo "###"
echo ""

echo "### postmannewman"
echo "###"
echo ""
