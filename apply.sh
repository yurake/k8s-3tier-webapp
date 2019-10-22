#!/bin/sh

echo "## default"

echo "### mysql"
docker build -t default/mysql:v0.0.1 ./mysql/.
kubectl apply -f ./mysql/mysql-pv.yaml
kubectl apply -f ./mysql/mysql-pvc.yaml
kubectl apply -f ./mysql/mysql-configmap.yaml
kubectl apply -f ./mysql/mysql-secret.yaml
kubectl apply -f ./mysql/mysql-deployment.yaml
kubectl apply -f ./mysql/mysql-service.yaml

echo "### redis"
kubectl apply -f ./redis/redis-deployment.yaml
kubectl apply -f ./redis/redis-service.yaml

echo "### rabbitmq"
docker build -t default/rabbitmq:v0.0.1 ./rabbitmq/.
kubectl apply -f ./rabbitmq/rabbitmq-configmap.yaml
kubectl apply -f ./rabbitmq/rabbitmq-secret.yaml
kubectl apply -f ./rabbitmq/rabbitmq-deployment.yaml
kubectl apply -f ./rabbitmq/rabbitmq-service.yaml
kubectl apply -f ./rabbitmq/rabbitmq-ingress.yaml

echo "### memcached"
kubectl apply -f ./memcached/memcached-deployment.yaml
kubectl apply -f ./memcached/memcached-service.yaml

echo "### nginx"
docker build -t default/nginx:v0.0.1 ./nginx/.
kubectl apply -f ./nginx/nginx-deployment.yaml
kubectl apply -f ./nginx/nginx-service.yaml
kubectl apply -f ./nginx/nginx-ingress.yaml

echo "### rabbitmq-consumer"
docker build -t default/rabbitmq-consumer:v0.0.1 ./rabbitmq-consumer/.
kubectl apply -f ./rabbitmq-consumer/rabbitmq-consumer-deployment.yaml

echo "### wlp"
docker build -t default/wlp:v0.0.1 ./wlp/.
kubectl apply -f ./wlp/wlp-deployment.yaml
kubectl apply -f ./wlp/wlp-service.yaml
kubectl apply -f ./wlp/wlp-ingress.yaml

echo "### jenkins"
kubectl apply -f ./jenkins/jenkins-pv.yaml
kubectl apply -f ./jenkins/jenkins-pvc.yaml
kubectl apply -f ./jenkins/jenkins-deployment.yaml
kubectl apply -f ./jenkins/jenkins-service.yaml
kubectl apply -f ./jenkins/jenkins-ingress.yaml

echo "## monitoring"
kubectl apply -f ./monitoring/monitoring-namespace.yaml

echo "### node-exporter"
kubectl apply -f ./monitoring/node-exporter/node-exporter-pv-sys.yaml
kubectl apply -f ./monitoring/node-exporter/node-exporter-pvc-sys.yaml
kubectl apply -f ./monitoring/node-exporter/node-exporter-pv-proc.yaml
kubectl apply -f ./monitoring/node-exporter/node-exporter-pvc-proc.yaml
kubectl apply -f ./monitoring/node-exporter/node-exporter-daemonset.yaml
kubectl apply -f ./monitoring/node-exporter/node-exporter-service.yaml

echo "### kube-state-metrics"
kubectl apply -f ./monitoring/kube-state-metrics/kube-state-metrics-rbac.yaml
kubectl apply -f ./monitoring/kube-state-metrics/kube-state-metrics-deployment.yaml
kubectl apply -f ./monitoring/kube-state-metrics/kube-state-metrics-service.yaml

echo "### prometheus"
kubectl apply -f ./monitoring/prometheus/prometheus-rbac.yaml
kubectl apply -f ./monitoring/prometheus/prometheus-pv.yaml
kubectl apply -f ./monitoring/prometheus/prometheus-configmap.yaml
kubectl apply -f ./monitoring/prometheus/prometheus-statefulset.yaml
kubectl apply -f ./monitoring/prometheus/prometheus-service.yaml
kubectl apply -f ./monitoring/prometheus/prometheus-ingress.yaml

echo "### grafana"
kubectl apply -f ./monitoring/grafana/grafana-pv.yaml
kubectl apply -f ./monitoring/grafana/grafana-pvc.yaml
kubectl apply -f ./monitoring/grafana/grafana-deployment.yaml
kubectl apply -f ./monitoring/grafana/grafana-service.yaml
kubectl apply -f ./monitoring/grafana/grafana-ingress.yaml
