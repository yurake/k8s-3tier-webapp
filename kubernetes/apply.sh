#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

echo "## default"

echo "### mysql"
docker build -t default/mysql:v0.0.1 ./mysql/.
kubectl apply -f ./mysql/mysql-pv.yaml
kubectl apply -f ./mysql/mysql-pvc.yaml
kubectl apply -f ./mysql/mysql-configmap.yaml
kubectl apply -f ./mysql/mysql-secret.yaml
kubectl apply -f ./mysql/mysql-deployment.yaml
kubectl apply -f ./mysql/mysql-service.yaml
echo "###"
echo ""

echo "### postgres"
docker build -t default/postgres:v0.0.1 ./postgres/.
kubectl apply -f ./postgres/postgres-pv.yaml
kubectl apply -f ./postgres/postgres-pvc.yaml
kubectl apply -f ./postgres/postgres-configmap.yaml
kubectl apply -f ./postgres/postgres-secret.yaml
kubectl apply -f ./postgres/postgres-deployment.yaml
kubectl apply -f ./postgres/postgres-service.yaml
echo "###"
echo ""

echo "### redis"
kubectl apply -f ./redis/redis-deployment.yaml
kubectl apply -f ./redis/redis-service.yaml
echo "###"
echo ""

echo "### rabbitmq"
docker build -t default/rabbitmq:v0.0.1 ./rabbitmq/.
kubectl apply -f ./rabbitmq/rabbitmq-configmap.yaml
kubectl apply -f ./rabbitmq/rabbitmq-secret.yaml
kubectl apply -f ./rabbitmq/rabbitmq-deployment.yaml
kubectl apply -f ./rabbitmq/rabbitmq-service.yaml
kubectl apply -f ./rabbitmq/rabbitmq-ingress.yaml
echo "###"
echo ""

echo "### activemq"
kubectl apply -f ./activemq/activemq-deployment.yaml
kubectl apply -f ./activemq/activemq-service.yaml
kubectl apply -f ./activemq/activemq-ingress.yaml
echo "###"
echo ""

echo "### hazelcast"
kubectl apply -f ./hazelcast/hazelcast-rbac.yaml
kubectl apply -f ./hazelcast/hazelcast-configmap.yaml
kubectl apply -f ./hazelcast/hazelcast-statefulset.yaml
kubectl apply -f ./hazelcast/hazelcast-service.yaml
echo "###"
echo ""

echo "### memcached"
kubectl apply -f ./memcached/memcached-deployment.yaml
kubectl apply -f ./memcached/memcached-service.yaml
echo "###"
echo ""

echo "### nginx"
docker build -t default/nginx:v0.0.1 ./nginx/.
kubectl apply -f ./nginx/nginx-deployment.yaml
kubectl apply -f ./nginx/nginx-service.yaml
kubectl apply -f ./nginx/nginx-ingress.yaml
echo "###"
echo ""

echo "### rabbitmq-consumer"
cp -p ../application/rabbitmq-consumer/target/rabbitmq-consumer.jar ./rabbitmq-consumer/.
docker build -t default/rabbitmq-consumer:v0.0.1 ./rabbitmq-consumer/.
kubectl apply -f ./rabbitmq-consumer/rabbitmq-consumer-deployment.yaml
echo "###"
echo ""

echo "### wlp"
cp -p ../application/wlp-web-java-spring/target/spring.war ./wlp/.
docker build -t default/wlp:v0.0.1 ./wlp/.
kubectl apply -f ./wlp/wlp-configmap.yaml
kubectl apply -f ./wlp/wlp-deployment.yaml
kubectl apply -f ./wlp/wlp-service.yaml
kubectl apply -f ./wlp/wlp-ingress.yaml
echo "###"
echo ""

echo "### jaxrs-quarkus"
cd ../application/jaxrs-quarkus
docker build -t default/jaxrs-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}
kubectl apply -f ./jaxrs-quarkus/jaxrs-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-quarkus/jaxrs-quarkus-service.yaml
kubectl apply -f ./jaxrs-quarkus/jaxrs-quarkus-ingress.yaml
echo "###"
echo ""

echo "### redis-mysql-quarkus"
cd ../application/redis-mysql-quarkus
docker build -t default/redis-mysql-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}
kubectl apply -f ./redis-mysql-quarkus/redis-mysql-quarkus-deployment.yaml
echo "###"
echo ""

echo "### redis-mysql-helidon"
cd ../application/redis-mysql-helidon
# docker build -t default/redis-mysql-helidon:v0.0.1 .
cd ${ROOT_DIR}
kubectl apply -f ./redis-mysql-helidon/redis-mysql-helidon-deployment.yaml
echo "###"
echo ""

echo "### hazelcast-mysql-quarkus"
cd ../application/hazelcast-mysql-quarkus
docker build -t default/hazelcast-mysql-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}
kubectl apply -f ./hazelcast-mysql-quarkus/hazelcast-mysql-quarkus-deployment.yaml
echo "###"
echo ""

echo "### activemq-mysql-quarkus"
cd ../application/activemq-mysql-quarkus
docker build -t default/activemq-mysql-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}
kubectl apply -f ./activemq-mysql-quarkus/activemq-mysql-quarkus-deployment.yaml
echo "###"
echo ""

echo "### randompublish-quarkus"
cd ../application/randompublish-quarkus
docker build -t default/randompublish-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}
kubectl apply -f ./randompublish-quarkus/randompublish-quarkus-deployment.yaml
kubectl apply -f ./randompublish-quarkus/randompublish-quarkus-service.yaml
kubectl apply -f ./randompublish-quarkus/randompublish-quarkus-ingress.yaml
echo "###"
echo ""

echo "### scheduled-quarkus"
cd ../application/scheduled-quarkus
docker build -t default/scheduled-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}
kubectl apply -f ./scheduled-quarkus/scheduled-quarkus-deployment.yaml
echo "###"
echo ""

echo "### jenkins"
kubectl apply -f ./jenkins/jenkins-pv.yaml
kubectl apply -f ./jenkins/jenkins-pvc.yaml
kubectl apply -f ./jenkins/jenkins-deployment.yaml
kubectl apply -f ./jenkins/jenkins-service.yaml
kubectl apply -f ./jenkins/jenkins-ingress.yaml
echo "###"
echo ""

echo "## monitoring"
kubectl apply -f ./monitoring/monitoring-namespace.yaml
echo "###"
echo ""

echo "### node-exporter"
kubectl apply -f ./monitoring/node-exporter/node-exporter-pv-sys.yaml
kubectl apply -f ./monitoring/node-exporter/node-exporter-pvc-sys.yaml
kubectl apply -f ./monitoring/node-exporter/node-exporter-pv-proc.yaml
kubectl apply -f ./monitoring/node-exporter/node-exporter-pvc-proc.yaml
kubectl apply -f ./monitoring/node-exporter/node-exporter-daemonset.yaml
kubectl apply -f ./monitoring/node-exporter/node-exporter-service.yaml
echo "###"
echo ""

echo "### kube-state-metrics"
kubectl apply -f ./monitoring/kube-state-metrics/kube-state-metrics-rbac.yaml
kubectl apply -f ./monitoring/kube-state-metrics/kube-state-metrics-deployment.yaml
kubectl apply -f ./monitoring/kube-state-metrics/kube-state-metrics-service.yaml
echo "###"
echo ""

echo "### prometheus"
kubectl apply -f ./monitoring/prometheus/prometheus-rbac.yaml
kubectl apply -f ./monitoring/prometheus/prometheus-pv.yaml
kubectl apply -f ./monitoring/prometheus/prometheus-configmap.yaml
kubectl apply -f ./monitoring/prometheus/prometheus-statefulset.yaml
kubectl apply -f ./monitoring/prometheus/prometheus-service.yaml
kubectl apply -f ./monitoring/prometheus/prometheus-ingress.yaml
echo "###"
echo ""

echo "### grafana"
kubectl apply -f ./monitoring/grafana/grafana-pv.yaml
kubectl apply -f ./monitoring/grafana/grafana-pvc.yaml
kubectl apply -f ./monitoring/grafana/grafana-deployment.yaml
kubectl apply -f ./monitoring/grafana/grafana-service.yaml
kubectl apply -f ./monitoring/grafana/grafana-ingress.yaml
echo "###"
echo ""

echo "### jupyter"
kubectl apply -f ./monitoring/jupyter/jupyter-pv.yaml
kubectl apply -f ./monitoring/jupyter/jupyter-pvc.yaml
kubectl apply -f ./monitoring/jupyter/jupyter-configmap.yaml
kubectl apply -f ./monitoring/jupyter/jupyter-deployment.yaml
kubectl apply -f ./monitoring/jupyter/jupyter-service.yaml
kubectl apply -f ./monitoring/jupyter/jupyter-ingress.yaml
echo "###"
echo ""

echo "### docker rmi tag:none"
docker image prune -f
echo "###"
echo ""

echo "### restart all pods"
# kubectl get pods | awk '{print $1}' | grep -v NAME | xargs kubectl delete pods
# kubectl get pods -n monitoring | awk '{print $1}' | grep -v NAME | xargs kubectl delete pods -n monitoring
echo "###"
