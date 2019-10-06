# k8s-3tier-webapp

## About
Sample web application based on k8s.
Use some docker images below:
* websphere-liberty:webProfile8
* memcached:1.5
* mysql:8.0
* rabbitmq:3.7.7-management
* openjdk:8-alpine

## Requirements
Installed CLI commands.
* docker
* kubectl
* minikube

## Build
`docker build`

* websphere-liberty
```
cd wlp
docker build -t default/wlp:v0.0.1 .
```

* mysql
```
cd mysql
docker build -t default/mysql:v0.0.1 .
```

* rabbitmq
```
cd rabbitmq
docker build -t default/rabbitmq:v0.0.1 .
```

* openjdk
```
cd batch
docker build -t default/batch:v0.0.1 .
```

## Apply
`kubectl apply`
```
find . -name "*.yaml"|xargs -I {} kubectl apply -f {}
```

### Run on Minikube
```bash
eval $(minikube docker-env)

minikube addons enable ingress

minikube config set memory 8192
minikube config set cpus 4
minikube config set disk-size 40g
minikube start
```

### Web Console
Add IP, domain in /etc/hosts
```
echo `minikube ip` k8s.3tier.webapp alertmanager.minikube prometheus.minikube grafana.minikube >> /etc/hosts
```
`http://k8s.3tier.webapp/`  
![top.png](./docs/top.png)