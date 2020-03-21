#!/bin/sh
set -eu

ROOT_DIR=$(cd $(dirname -- $0); pwd)

echo "## default"

echo "### mysql"
cd mysql
docker build -t default/mysql:v0.0.1 .
kubectl apply -f ./mysql-pv.yaml
kubectl apply -f ./mysql-pvc.yaml
kubectl apply -f ./mysql-configmap.yaml
kubectl apply -f ./mysql-secret.yaml
kubectl apply -f ./mysql-deployment.yaml
kubectl apply -f ./mysql-service.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### postgres"
cd postgres
docker build -t default/postgres:v0.0.1 .
kubectl apply -f ./postgres-pv.yaml
kubectl apply -f ./postgres-pvc.yaml
kubectl apply -f ./postgres-configmap.yaml
kubectl apply -f ./postgres-secret.yaml
kubectl apply -f ./postgres-deployment.yaml
kubectl apply -f ./postgres-service.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### redis"
cd redis
kubectl apply -f ./redis-deployment.yaml
kubectl apply -f ./redis-service.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### rabbitmq"
cd rabbitmq
docker build -t default/rabbitmq:v0.0.1 .
kubectl apply -f ./rabbitmq-configmap.yaml
kubectl apply -f ./rabbitmq-secret.yaml
kubectl apply -f ./rabbitmq-deployment.yaml
kubectl apply -f ./rabbitmq-service.yaml
kubectl apply -f ./rabbitmq-ingress.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### activemq"
cd activemq
kubectl apply -f ./activemq-deployment.yaml
kubectl apply -f ./activemq-service.yaml
kubectl apply -f ./activemq-ingress.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### hazelcast"
cd hazelcast
docker build -t default/hazelcast:v0.0.1 .
kubectl apply -f ./hazelcast-statefulset.yaml
kubectl apply -f ./hazelcast-service.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### memcached"
cd memcached
kubectl apply -f ./memcached-deployment.yaml
kubectl apply -f ./memcached-service.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### nginx"
cd nginx
docker build -t default/nginx:v0.0.1 .
kubectl apply -f ./nginx-deployment.yaml
kubectl apply -f ./nginx-service.yaml
kubectl apply -f ./nginx-ingress.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### rabbitmq-mysql-quarkus"
cd ../application/rabbitmq-mysql-quarkus
docker build -t default/rabbitmq-mysql-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}/rabbitmq-mysql-quarkus
kubectl apply -f ./rabbitmq-mysql-quarkus-deployment.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### wlp"
cd ${ROOT_DIR}/wlp
\cp -p ../../application/wlp-web-java-spring/target/spring.war .
docker build -t default/wlp:v0.0.1 .
kubectl apply -f ./wlp-configmap.yaml
kubectl apply -f ./wlp-deployment.yaml
kubectl apply -f ./wlp-service.yaml
kubectl apply -f ./wlp-ingress.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jaxrs-quarkus"
cd ../application/jaxrs-quarkus
docker build -t default/jaxrs-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}/jaxrs-quarkus
kubectl apply -f ./jaxrs-quarkus-deployment.yaml
kubectl apply -f ./jaxrs-quarkus-service.yaml
kubectl apply -f ./jaxrs-quarkus-service-externalname.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### redis-mysql-quarkus"
cd ../application/redis-mysql-quarkus
docker build -t default/redis-mysql-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}/redis-mysql-quarkus
kubectl apply -f ./redis-mysql-quarkus-deployment.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### redis-mysql-helidon"
cd ../application/redis-mysql-helidon
cd ${ROOT_DIR}/redis-mysql-helidon
kubectl apply -f ./redis-mysql-helidon-deployment.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### hazelcast-mysql-quarkus"
cd ../application/hazelcast-mysql-quarkus
docker build -t default/hazelcast-mysql-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}/hazelcast-mysql-quarkus
kubectl apply -f ./hazelcast-mysql-quarkus-deployment.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### activemq-mysql-quarkus"
cd ../application/activemq-mysql-quarkus
docker build -t default/activemq-mysql-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}/activemq-mysql-quarkus
kubectl apply -f ./activemq-mysql-quarkus-deployment.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### randompublish-quarkus"
cd ../application/randompublish-quarkus
docker build -t default/randompublish-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}/randompublish-quarkus
kubectl apply -f ./randompublish-quarkus-deployment.yaml
kubectl apply -f ./randompublish-quarkus-service.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### scheduled-quarkus"
cd ../application/scheduled-quarkus
docker build -t default/scheduled-quarkus:v0.0.1 -f src/main/docker/Dockerfile.jvm .
cd ${ROOT_DIR}/scheduled-quarkus
kubectl apply -f ./scheduled-quarkus-deployment.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "## jaeger"
cd jaeger
kubectl apply -f ./jaeger-all-in-one-template.yml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "## monitoring"
cd ${ROOT_DIR}/monitoring
kubectl apply -f ./monitoring-namespace.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jenkins"
cd ${ROOT_DIR}/monitoring/jenkins
docker build -t monitoring/jenkins:v0.0.1 .
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
cd ${ROOT_DIR}
kubectl apply -f ./monitoring/grafana/grafana-pv.yaml
kubectl apply -f ./monitoring/grafana/grafana-pvc.yaml
kubectl apply -f ./monitoring/grafana/grafana-deployment.yaml
kubectl apply -f ./monitoring/grafana/grafana-service.yaml
kubectl apply -f ./monitoring/grafana/grafana-ingress.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### jupyter"
cd ${ROOT_DIR}
kubectl apply -f ./monitoring/jupyter/jupyter-pv.yaml
kubectl apply -f ./monitoring/jupyter/jupyter-pvc.yaml
kubectl apply -f ./monitoring/jupyter/jupyter-configmap.yaml
kubectl apply -f ./monitoring/jupyter/jupyter-deployment.yaml
kubectl apply -f ./monitoring/jupyter/jupyter-service.yaml
kubectl apply -f ./monitoring/jupyter/jupyter-ingress.yaml
cd ${ROOT_DIR}
echo "###"
echo ""

echo "### ab"
cd ${ROOT_DIR}/monitoring/test/ab
docker build -t monitoring/ab:v0.0.1 .
cd ${ROOT_DIR}
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
