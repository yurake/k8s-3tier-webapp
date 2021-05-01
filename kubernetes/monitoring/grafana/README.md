## Grafana dashboard settings
Choose data source type > Prometheus  
[HTTP] > [URL] > `http://prometheus:9090`  
[HTTP] > [Access] > `Server(default)`  

Choose data source type > Jaeger  
[HTTP] > [URL] > `http://jaeger-query.default.svc.cluster.local`  

## Recommended grafana dashboards
https://grafana.com/grafana/dashboards
* 455  
Postgres Overview
* 763  
Redis Dashboard for Prometheus Redis Exporter 1.x
* 2583
MongoDB
* 3066  
JVM overview
* 6239  
Mysql
* 8588  
Kubernetes Deployment Statefulset Daemonset metrics
* 8685  
K8s Cluster Summary
* 9789  
NGINX Ingress controller
* 10000  
Cluster Monitoring for Kubernetes
* 10001  
Jaeger
* 10465  
ZooKeeper
* 10991  
RabbitMQ

## Custom dashboards
[dashboards](./dashboard)
