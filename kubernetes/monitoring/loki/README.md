https://github.com/grafana/loki/tree/master/production/helm

## Install

```bash
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update
helm upgrade --install loki --namespace=monitoring grafana/loki
```

## Grafana dashboard settings

Choose data source type > Loki  
[HTTP] > [URL] > `http://loki:3100`
