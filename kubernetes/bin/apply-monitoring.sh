#!/bin/sh
set -eu

ROOT_DIR="$(
	cd "$(dirname -- "$0")"
	cd ..
	pwd
)"

cd "$ROOT_DIR"/bin
./apply-monitoring-namespace.sh

echo "### jenkins"
cd "${ROOT_DIR}"/monitoring/jenkins
kubectl apply -f ./jenkins-rbac.yaml
kubectl apply -f ./jenkins-pv.yaml
kubectl apply -f ./jenkins-pvc.yaml
kubectl apply -f ./jenkins-deployment.yaml
kubectl apply -f ./jenkins-service.yaml
kubectl apply -f ./jenkins-ingress.yaml
cd "${ROOT_DIR}"
echo "###"
echo ""

echo "### node-exporter"
cd "${ROOT_DIR}"/monitoring/node-exporter
kubectl apply -f ./node-exporter-pv-sys.yaml
kubectl apply -f ./node-exporter-pvc-sys.yaml
kubectl apply -f ./node-exporter-pv-proc.yaml
kubectl apply -f ./node-exporter-pvc-proc.yaml
kubectl apply -f ./node-exporter-daemonset.yaml
kubectl apply -f ./node-exporter-service.yaml
cd "${ROOT_DIR}"
echo "###"
echo ""

echo "### kube-state-metrics"
cd "${ROOT_DIR}"/monitoring/kube-state-metrics
kubectl apply -f ./kube-state-metrics-rbac.yaml
kubectl apply -f ./kube-state-metrics-deployment.yaml
kubectl apply -f ./kube-state-metrics-service.yaml
cd "${ROOT_DIR}"
echo "###"
echo ""

echo "### prometheus"
cd "${ROOT_DIR}"/monitoring/prometheus
kubectl apply -f ./prometheus-rbac.yaml
kubectl apply -f ./prometheus-pv.yaml
kubectl apply -f ./prometheus-configmap.yaml
kubectl apply -f ./prometheus-statefulset.yaml
kubectl apply -f ./prometheus-service.yaml
kubectl apply -f ./prometheus-ingress.yaml
cd "${ROOT_DIR}"
echo "###"
echo ""

echo "### grafana"
cd "${ROOT_DIR}"/monitoring/grafana
kubectl apply -f ./grafana-pv.yaml
kubectl apply -f ./grafana-pvc.yaml
kubectl apply -f ./grafana-deployment.yaml
kubectl apply -f ./grafana-service.yaml
kubectl apply -f ./grafana-ingress.yaml
cd "${ROOT_DIR}"
echo "###"
echo ""

echo "### jupyter"
cd "${ROOT_DIR}"/monitoring/jupyter
kubectl apply -f ./jupyter-pv.yaml
kubectl apply -f ./jupyter-pvc.yaml
kubectl apply -f ./jupyter-configmap.yaml
kubectl apply -f ./jupyter-deployment.yaml
kubectl apply -f ./jupyter-service.yaml
kubectl apply -f ./jupyter-ingress.yaml
cd "${ROOT_DIR}"
echo "###"
echo ""

echo "### ab"
echo "###"
echo ""

echo "### postmannewman"
echo "###"
echo ""
