<https://github.com/grafana/tempo/tree/main/example/helm>

## Install

```bash
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update
helm upgrade --install tempo grafana/tempo
```

## Add ExternalName Resource

```bash
kubectl apply -f tempo-service-externalname-default.yaml
```

## Grafana dashboard settings

Choose data source type > Loki  
[HTTP] > [URL] > `http://tempo-monitoring:3100`

## Change Trace Settings in Qruarkus Application

-   Logging trace ids in application logs

application.properties

```properties
- quarkus.log.console.format=%d{HH:mm:ss} %-5p [%c{2.}] (%t) %s%e%n
+ quarkus.log.console.format=%d{HH:mm:ss} %-5p traceId=%X{traceId}, parentId=%X{parentId}, spanId=%X{spanId}, sampled=%X{sampled} [%c{2.}] (%t) %s%e%n
```

-   Change target tracing endpoint

application.properties

```properties
- quarkus.jaeger.agent-host-port=jaeger-agent:6831
+ quarkus.jaeger.agent-host-port=tempo:6831
```
