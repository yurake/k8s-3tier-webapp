# Download at

<https://raw.githubusercontent.com/jaegertracing/jaeger-kubernetes/master/all-in-one/jaeger-all-in-one-template.yml>

## Add resource

Cahnge LoadBalancer to Service IP  
Add Ingress Resource

## Grafana dashboard settings

Choose data source type > Jaeger  
[HTTP] > [URL] > `http://jaeger-query-monitoring:80`
