# :deer: k8s-3tier-webapp

[![Java CI](https://github.com/yurake/k8s-3tier-webapp/workflows/Java%20CI/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Java+CI%22)
[![Docker Image CI](https://github.com/yurake/k8s-3tier-webapp/workflows/Docker%20Image%20CI/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Docker+Image+CI%22)
[![Docker Vulnerability Scan](https://github.com/yurake/k8s-3tier-webapp/workflows/Docker%20Vulnerability%20Scan/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Docker+Vulnerability+Scan%22)
[![Yaml Validator](https://github.com/yurake/k8s-3tier-webapp/workflows/Yaml%20Validator/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Yaml+Validator%22)  
[![Minikube CI](https://github.com/yurake/k8s-3tier-webapp/workflows/Minikube%20CI/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Minikube+CI%22)
[![Check for Update](https://github.com/yurake/k8s-3tier-webapp/workflows/Check%20for%20Update/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Check+for+Update%22)
[![Dockerfile Lint](https://github.com/yurake/k8s-3tier-webapp/workflows/Dockerfile%20Lint/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Dockerfile+Lint%22)
[![Check for CI Target](https://github.com/yurake/k8s-3tier-webapp/workflows/Check%20for%20CI%20Target/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Check+for+CI+Target%22)  
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6549c4c8ae5a4abd8ae052cb5c62d388)](https://app.codacy.com/manual/yurake/k8s-3tier-webapp?utm_source=github.com&utm_medium=referral&utm_content=yurake/k8s-3tier-webapp&utm_campaign=Badge_Grade_Settings)
[![CodeFactor](https://www.codefactor.io/repository/github/yurake/k8s-3tier-webapp/badge)](https://www.codefactor.io/repository/github/yurake/k8s-3tier-webapp)
[![Maintainability](https://api.codeclimate.com/v1/badges/64a1de96c5eb777b9db1/maintainability)](https://codeclimate.com/github/yurake/k8s-3tier-webapp/maintainability)
[![codebeat badge](https://codebeat.co/badges/e0bfc464-3370-467d-910f-ade9d83265b1)](https://codebeat.co/projects/github-com-yurake-k8s-3tier-webapp-master)
[![](https://www.code-inspector.com/project/11254/score/svg)](https://frontend.code-inspector.com/project/11254/dashboard)
[![](https://www.code-inspector.com/project/11254/status/svg)](https://frontend.code-inspector.com/project/11254/techdebt)  
[![codecov](https://codecov.io/gh/yurake/k8s-3tier-webapp/branch/master/graph/badge.svg)](https://codecov.io/gh/yurake/k8s-3tier-webapp)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=yurake_k8s-3tier-webapp&metric=alert_status)](https://sonarcloud.io/organizations/yurak/projects)
[![k8s-3tier-webapp](https://img.shields.io/endpoint?url=https://dashboard.cypress.io/badge/simple/uv6uzj/master&style=flat&logo=cypress)](https://dashboard.cypress.io/projects/uv6uzj/runs)
[![Renovate enabled](https://img.shields.io/badge/renovate-enabled-brightgreen.svg)](https://app.renovatebot.com/dashboard#github/yurake/k8s-3tier-webapp)
[![Gitpod ready-to-code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/yurake/k8s-3tier-webapp)
[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)

Sample web application based on k8s.  
Focus on connecting components, setting k8s resources, and aiming to microservices.  
The k8s-3tier-webapp service is for PoC, not production ready.

| Tier | Name | Description |
|:-:|:-:|:-:|
| 1 | Frontend | Web page, Application logic, Static file
| 2 | Backend | Database, Cache, Messaging
| 3 | Management | Monitoring, CI/CD, Tracing

![LayardArchitecturalOverview.png](./docs/LayardArchitecturalOverview.png)

## Concepts
* Choice better architecture what you expect based on k8s
* Help understanding k8s resources based on yaml
* Test automation on Github Actions for free
* Expand test automation - unit, end-to-end, performance, recovery, security
* Keep observability for monitoring services
* Improve application quality using public CI services that following Github

## Features
* All docker components run on k8s
* All application written by Java mostly Quarkus
* k8s resources written by yaml, try not to use helm, operator
* Use only public software, not commercial
* Use Github Actions for build, test, release


## Requirements
Installed CLI commands.
* :computer: docker
* :computer: kubectl
* :computer: minikube

## Run on Minikube

**Mac**  
```bash
minikube config set memory 121288
minikube config set cpus 6
minikube config set disk-size 80g
minikube start --kubernetes-version=v1.18.3
minikube addons enable ingress

eval $(minikube docker-env)
```

**Windows**  
```bash
minikube config set memory 121288
minikube config set cpus 6
minikube config set disk-size 80g
minikube start --kubernetes-version=v1.18.3
minikube addons enable ingress

minikube docker-env --shell powershell | Invoke-Expression
```

## Build & Apply

`apply.sh` supports `kubectl apply` to k8s clusters.
```
./kubernetes/apply.sh
```

## Web Console Access

### Add hosts
Add IP, domain in /etc/hosts
```
echo `minikube ip` k8s.3tier.webapp wlp.minikube api.server.minikube rabbitmq.management.minikube jenkins.minikube alertmanager.minikube prometheus.minikube grafana.minikube jupyter.minikube hazelcast.manager.minikube activemq.management.minikube jaeger.minikube argo.minikube >> /etc/hosts
```

#### Web Console list
* :triangular_flag_on_post: Top Page  
http://k8s.3tier.webapp/
![top.png](./docs/top.png)

* :triangular_flag_on_post: Open Liberty  
http://wlp.minikube  
* :triangular_flag_on_post: Rabbitmq Management Console  
http://rabbitmq.management.minikube  
* :triangular_flag_on_post: Activemq Management Console  
http://activemq.management.minikube  

## Application
Java based web application for [application](application/README.md)

## Kubernetes
Yaml based kubernetes manifests for [kubernetes](kubernetes/README.md)

## Monitoring
Set up [monitoring](kubernetes/monitoring/README.md)
