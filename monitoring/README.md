## About
Sample monitoring application based on k8s.
Use some docker images below:
* prom/prometheus:v2.11.1
* grafana/grafana:6.2.5
* busybox:latest
* prom/node-exporter:v0.15.2
* quay.io/coreos/kube-state-metrics:v1.8.0

## Build & Apply
No need to do.

## Web Console
* prometheus  
http://prometheus.minikube/
* grafana  
http://grafana.minikube/

## Prometheus Dashboard
* Custom  
[grafana-minikube-dashboard.json](grafana/grafana-minikube-dashboard.json)
* Public  
https://grafana.com/grafana/dashboards/8685
* For Web Application  
https://github.com/IBM/charts/blob/master/stable/ibm-websphere-liberty/ibm_cloud_pak/pak_extensions/dashboards/ibm-websphere-liberty-grafana-dashboard.json