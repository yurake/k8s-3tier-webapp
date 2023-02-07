# 🦌 k8s-3tier-webapp

Sample web application based on k8s. Focus on connecting components, setting k8s resources, and aiming to microservices. The k8s-3tier-webapp service is for PoC, not production ready.

[![Java CI](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/java-ci.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/java-ci.yml) 
[![Docker Image CI](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/docker-image-ci.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/docker-image-ci.yml) 
[![Minikube Quarkus CI](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/minikube-quarkus-ci.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/minikube-quarkus-ci.yml) 
[![Minikube Yaml CI](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/minikube-yaml-ci.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/minikube-yaml-ci.yml) 
[![Minikube Stress CI](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/minikube-stress-ci.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/minikube-stress-ci.yml) 
[![Minikube Security CI](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/minikube-security-ci.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/minikube-security-ci.yml) 
[![kind Chaos Mesh CI](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/kind-chaos-mesh-ci.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/kind-chaos-mesh-ci.yml) 
[![kind Quarkus CI](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/kind-quarkus-ci.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/kind-quarkus-ci.yml) 
[![kind Yaml CI](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/kind-yaml-ci.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/kind-yaml-ci.yml) 
[![Yaml Validator](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/yaml-validator.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/yaml-validator.yml) 
[![Shell Validator](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/shell-validator.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/shell-validator.yml) 
[![Dockerfile Lint](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/dockerfile-lint.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/dockerfile-lint.yml) 
[![CodeQL](https://github.com/yurake/k8s-3tier-webapp/workflows/CodeQL/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/codeql.yml) 
[![Check for Update](https://github.com/yurake/k8s-3tier-webapp/workflows/Check%20for%20Update/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Check+for+Update%22) 
[![CIS Dockerfile benchmark](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/cis-dockerfile-benchmark.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/cis-dockerfile-benchmark.yml) 
[![Cypress CI](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/cypress-ci.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/cypress-ci.yml) 
[![K8S Security Config Watch](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/k8s-security-config-watch.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/k8s-security-config-watch.yml) 
[![Dependency Review](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/dependency-review.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/dependency-review.yml) 
[![pmd](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/pmd.yml/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/pmd.yml) 
[![pages-build-deployment](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/yurake/k8s-3tier-webapp/actions/workflows/pages/pages-build-deployment) 
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2844382aa110487e94bba8369267476e)](https://www.codacy.com/gh/yurake/k8s-3tier-webapp/dashboard?utm\_source=github.com\&utm\_medium=referral\&utm\_content=yurake/k8s-3tier-webapp\&utm\_campaign=Badge\_Grade) 
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/2844382aa110487e94bba8369267476e)](https://www.codacy.com/gh/yurake/k8s-3tier-webapp/dashboard?utm\_source=github.com\&utm\_medium=referral\&utm\_content=yurake/k8s-3tier-webapp\&utm\_campaign=Badge\_Coverage) 
[![CodeFactor](https://www.codefactor.io/repository/github/yurake/k8s-3tier-webapp/badge)](https://www.codefactor.io/repository/github/yurake/k8s-3tier-webapp) [![Maintainability](https://api.codeclimate.com/v1/badges/64a1de96c5eb777b9db1/maintainability)](https://codeclimate.com/github/yurake/k8s-3tier-webapp/maintainability) 
[![codebeat badge](https://codebeat.co/badges/e0bfc464-3370-467d-910f-ade9d83265b1)](https://codebeat.co/projects/github-com-yurake-k8s-3tier-webapp-master) 
[![codecov](https://codecov.io/gh/yurake/k8s-3tier-webapp/branch/master/graph/badge.svg)](https://codecov.io/gh/yurake/k8s-3tier-webapp) 
[![Codiga Code Quality Score](https://api.codiga.io/project/34687/score/svg)](https://app.codiga.io) 
[![Codiga Code Grade](https://api.codiga.io/project/34687/status/svg)](https://app.codiga.io) 
[![DeepSource Active Issues](https://deepsource.io/gh/yurake/k8s-3tier-webapp.svg/?label=active+issues\&show\_trend=true\&token=Y64jIS9a54isgV4hi4\_uuerZ)](https://deepsource.io/gh/yurake/k8s-3tier-webapp/?ref=repository-badge) 
[![DeepSource Resluved Issues](https://deepsource.io/gh/yurake/k8s-3tier-webapp.svg/?label=resolved+issues\&show\_trend=true\&token=Y64jIS9a54isgV4hi4\_uuerZ)](https://deepsource.io/gh/yurake/k8s-3tier-webapp/?ref=repository-badge) 
[![Cypress](https://img.shields.io/endpoint?url=https://dashboard.cypress.io/badge/detailed/7rgxn6/master\&style=flat\&logo=cypress)](https://dashboard.cypress.io/projects/7rgxn6/runs)   
[![Support JVM Version](https://img.shields.io/badge/JVM-17-yellow.svg?style=flat\&logo=Java)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Java+CI%22) 
[![Support Quarkus Version](https://img.shields.io/badge/Quarkus-2.16.1-yellow.svg?style=flat\&logo=Quarkus)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Java+CI%22) 
[![Support Kubernetes Version](https://img.shields.io/badge/Kubernetes-1.26.1-yellow.svg?style=flat\&logo=Kubernetes)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Minikube+CI%22) 
[![Support Minikube Version](https://img.shields.io/badge/Minikube-1.27-yellow.svg?style=flat\&logo=Kubernetes)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22Minikube+CI%22) 
[![Support kind Version](https://img.shields.io/badge/kind-2.5.1-yellow.svg?style=flat\&logo=Kubernetes)](https://github.com/yurake/k8s-3tier-webapp/actions?query=workflow%3A%22kind+CI%22) 
[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE/) 
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fyurake%2Fk8s-3tier-webapp.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Fyurake%2Fk8s-3tier-webapp?ref=badge\_shield)  
[![AccessLint](https://img.shields.io/badge/AccessLint-enabled-blue?style=flat)](https://accesslint.com/accounts/yurake/projects/k8s-3tier-webapp)
[![Datadog](https://img.shields.io/badge/Datadog-enabled-blue?style=flat\&logo=datadog)](https://app.datadoghq.com/ci/test-services)
[![Debricked](https://img.shields.io/badge/Debricked-enabled-blue?style=flat)](https://debricked.com/app/en/repository/40176)
[![GitBook](https://img.shields.io/badge/GitBook-enabled-blue?style=flat\&logo=gitbook)](https://www.gitbook.com)
[![Gitpod](https://img.shields.io/badge/Gitpod-ready-blue?style=flat\&logo=gitpod)](https://gitpod.io/#https://github.com/yurake/k8s-3tier-webapp) 
[![Hound](https://img.shields.io/badge/Hound-enabled-blue?style=flat)](https://houndci.com/repos)
[![Imgbot](https://img.shields.io/badge/Imgbot-enabled-blue?style=flat)](https://imgbot.net/app/)
[![Renovate](https://img.shields.io/badge/Renovate-enabled-blue?style=flat\&logo=renovatebot)](https://app.renovatebot.com/dashboard#github/yurake/k8s-3tier-webapp)
[![Repo Ranger](https://img.shields.io/badge/Repo%20Ranger-enabled-blue?style=flat)](https://reporanger.com/)
[![Socket](https://img.shields.io/badge/Socket-enabled-blue?style=flat\&logo=socketdotio)](https://socket.dev/dashboard/org/gh/yurake/repo/k8s-3tier-webapp)
[![Sonatype Lift](https://img.shields.io/badge/Sonatype%20Lift-enabled-blue?style=flat)](https://lift.sonatype.com/results/github.com/yurake/k8s-3tier-webapp)
[![What The Diff](https://img.shields.io/badge/What%20The%20Diff-enabled-blue?style=flat)](https://app.whatthediff.ai/repository/181164153)  
[![sonarcloud](https://img.shields.io/badge/sonarcloud-enabled-orange?style=flat\&logo=sonarcloud)](https://sonarcloud.io/projects)   
More details at [sonarcloud](SONARCLOUD.md)
***

| Tier |    Name    |                Description               |
| :--: | :--------: | :--------------------------------------: |
|   1  |  Frontend  | Web page, Application logic, Static file |
|   2  |   Backend  |        Database, Cache, Messaging        |
|   3  | Management |        Monitoring, CI/CD, Tracing        |

![LayardArchitecturalOverview.png](docs/LayardArchitecturalOverview.png)

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

### Mac

```bash
minikube config set memory 121288
minikube config set cpus 6
minikube config set disk-size 80g
minikube start
minikube addons enable ingress
kubectl delete -A ValidatingWebhookConfiguration ingress-nginx-admission

eval $(minikube docker-env)
```

### Windows

```bash
minikube config set memory 121288
minikube config set cpus 6
minikube config set disk-size 80g
minikube start
minikube addons enable ingress
kubectl delete -A ValidatingWebhookConfiguration ingress-nginx-admission

minikube docker-env --shell powershell | Invoke-Expression
```

## Build & Apply

`apply.sh` supports `kubectl apply` to k8s clusters.

```
./kubernetes/bin/apply.sh
```

## Web Console Access

### Add hosts

Add IP, domain in /etc/hosts

```
echo `minikube ip` k8s.3tier.webapp api.server.minikube \
rabbitmq.management.minikube jenkins.minikube alertmanager.minikube prometheus.minikube \
grafana.minikube jupyter.minikube hazelcast.manager.minikube activemq.management.minikube \
jaeger.minikube argo.minikube >> /etc/hosts
```

### Web Console list

| Service                     | URL                                                                        |
| --------------------------- | -------------------------------------------------------------------------- |
| Top Page                    | [http://k8s.3tier.webapp/](http://k8s.3tier.webapp/)                       |
| Rabbitmq Management Console | [http://rabbitmq.management.minikube](http://rabbitmq.management.minikube) |
| Activemq Management Console | [http://activemq.management.minikube](http://activemq.management.minikube) |

![top.png](docs/top.png)

## Component

### Application

Java based web application for [application](application/)

### Kubernetes

Yaml based kubernetes manifests for [kubernetes](kubernetes/)

### Monitoring

Set up [monitoring](kubernetes/monitoring/) for Tier 3

## License

k8s-3tier-webapp is licensed under the MIT License. See [LICENSE](LICENSE/) for the full license text.

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Fyurake%2Fk8s-3tier-webapp.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2Fyurake%2Fk8s-3tier-webapp?ref=badge\_large)

## Contribution

1. [Fork](https://github.com/yurake/k8s-3tier-webapp/fork) this repo
2. Create a feature branch named like feature/enhancement from [master](https://github.com/yurake/k8s-3tier-webapp/tree/master) branch
3. Commit your changes
4. Rebase your local changes against the [master](https://github.com/yurake/k8s-3tier-webapp/tree/master) branch
5. Create new Pull Request

## Security

[Security](SECURITY.md)
