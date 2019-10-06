## About
Sample monitoring application based on k8s.
Use some docker images below:
* prom/prometheus:v2.11.1
* grafana/grafana:6.2.5
* busybox:latest
* prom/node-exporter:v0.15.2
* quay.io/coreos/kube-state-metrics:v1.8.0

## Build
No need to do.

## Apply
`kubectl apply`
```
find . -name "*.yaml"|xargs -I {} kubectl apply -f {}
```

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
