# k8s-3tier-webapp

## About
Sample web application based on k8s.
Use some docker images below:
* websphere-liberty:19.0.0.6-webProfile8
* memcached:1.5
* mysql:8.0
* rabbitmq:3.7.7-management
* openjdk:8-alpine
* nginx:1.17.4
* jenkins/jenkins:lts


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
`docker build`  
`kubectl apply`
```
./apply.sh
```

## Web Console Access

### Add hosts
Add IP, domain in /etc/hosts
```
echo `minikube ip` k8s.3tier.webapp wlp.minikube quarkus.minikube api.server.minikube rabbitmq.management.minikube jenkins.minikube alertmanager.minikube prometheus.minikube grafana.minikube jupyter.minikube rocketchat.minikube >> /etc/hosts
```

#### Web Application
http://k8s.3tier.webapp/
![top.png](./docs/top.png)

#### RabbitMQ Management
http://rabbitmq.management.minikube/

#### Jenkins
http://jenkins.minikube/

## See Also
Set up [monitoring](kubernetes/monitoring/README.md)
