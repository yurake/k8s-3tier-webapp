https://github.com/grafana/loki/tree/master/production/helm

## Install

```bash
helm repo add loki https://grafana.github.io/loki/charts
helm repo update
helm upgrade --install loki --namespace=monitoring loki/loki-stack
```

## Grafana dashboard settings

Choose data source type > Loki  
[HTTP] > [URL] > `http://loki:3100`
