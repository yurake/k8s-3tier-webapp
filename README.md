# k8s-3tier-webapp

![Java CI](https://github.com/yurake/k8s-3tier-webapp/workflows/Java%20CI/badge.svg)
![Docker Image CI](https://github.com/yurake/k8s-3tier-webapp/workflows/Docker%20Image%20CI/badge.svg)
![Docker Vulnerability Scan](https://github.com/yurake/k8s-3tier-webapp/workflows/Docker%20Vulnerability%20Scan/badge.svg)
![Yaml Validator](https://github.com/yurake/k8s-3tier-webapp/workflows/Yaml%20Validator/badge.svg)  
[![Build Status](https://travis-ci.com/yurake/k8s-3tier-webapp.svg?branch=master)](https://travis-ci.com/yurake/k8s-3tier-webapp)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=yurake_webapp-service&metric=alert_status)](https://sonarcloud.io/dashboard?id=yurake_webapp-service)
[![codecov](https://codecov.io/gh/yurake/k8s-3tier-webapp/branch/master/graph/badge.svg)](https://codecov.io/gh/yurake/k8s-3tier-webapp)
[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)

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
![diagram.png](./docs/diagram.png)

## Requirements
Installed CLI commands.
* docker
* kubectl
* minikube

## Run on Minikube

For Mac  
```bash
minikube config set memory 8192
minikube config set cpus 4
minikube config set disk-size 80g
minikube start --extra-config=kubelet.authentication-token-webhook=true --extra-config=kubelet.authorization-mode=Webhook --kubernetes-version=v1.15.4

minikube addons enable ingress

eval $(minikube docker-env)
```

For Windows  
```bash
minikube config set memory 8192
minikube config set cpus 4
minikube config set disk-size 80g
minikube start --extra-config=kubelet.authentication-token-webhook=true --extra-config=kubelet.authorization-mode=Webhook --kubernetes-version=v1.15.4

minikube addons enable ingress

minikube docker-env --shell powershell | Invoke-Expression
```

## Build & Apply

`install.sh` supports `docker build`, `kubectl apply` to k8s clusters.
```
./install.sh
```

## Web Console Access

### Add hosts
Add IP, domain in /etc/hosts
```
echo `minikube ip` k8s.3tier.webapp wlp.minikube api.server.minikube rabbitmq.management.minikube jenkins.minikube alertmanager.minikube prometheus.minikube grafana.minikube jupyter.minikube hazelcast.manager.minikube activemq.management.minikube jaeger.minikube argo.minikube >> /etc/hosts
```

#### Web Console list
* Top Page  
http://k8s.3tier.webapp/
![top.png](./docs/top.png)

* Open Liberty  
http://wlp.minikube  
* Rabbitmq Management Console  
http://rabbitmq.management.minikube  
* Activemq Management Console  
http://activemq.management.minikube  

## See Also
Set up [monitoring](kubernetes/monitoring/README.md)

## Github Actions on minikube [alpha]
![e2e Test on Minikube](https://github.com/yurake/k8s-3tier-webapp/workflows/e2e%20Test%20on%20Minikube/badge.svg)  
The workflow for end to end api test on minikube on Github Actions.  
The trriger is close issue.
