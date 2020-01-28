# k8s-3tier-webapp

https://github.com/yurake/k8s-3tier-webapp/workflows/Java%20CI/badge.svg

## About
Sample web application based on k8s.  
Focus on connecting components, setting k8s resources, and aiming to microservices.  
The k8s-3tier-webapp service is for PoC, not production ready.

| Tier | Name | Description |
|:-:|:-:|:-:|
| 1 | Frontend | Web page, Application logic, Static file
| 2 | Backend | Database, Cache, Messaging
| 3 | Management | Monitoring, CI/CD, Tracing

![LayardArchitecturalOverview.png](./docs/LayardArchitecturalOverview.png)

## Requirements
Installed CLI commands.
* docker
* kubectl
* minikube

## Run on Minikube
```bash
minikube config set memory 8192
minikube config set cpus 4
minikube config set disk-size 40g
minikube start --extra-config=kubelet.authentication-token-webhook=true --extra-config=kubelet.authorization-mode=Webhook --kubernetes-version=v1.15.4

minikube addons enable ingress

eval $(minikube docker-env)
```

## Build & Apply

`apply.sh` supports `docker build`, `kubectl apply` to k8s clusters.
```
./apply.sh
```

## Web Console Access

### Add hosts
Add IP, domain in /etc/hosts
```
echo `minikube ip` k8s.3tier.webapp wlp.minikube quarkus.minikube api.server.minikube rabbitmq.management.minikube jenkins.minikube alertmanager.minikube prometheus.minikube grafana.minikube jupyter.minikube rocketchat.minikube hazelcast.manager.minikube activemq.management.minikube jaeger.minikube >> /etc/hosts
```

#### Top Page
http://k8s.3tier.webapp/
![top.png](./docs/top.png)

## See Also
Set up [monitoring](kubernetes/monitoring/README.md)
