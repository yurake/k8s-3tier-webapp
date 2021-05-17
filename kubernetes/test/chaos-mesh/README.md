# Chaos Mesh

## Reference
See installaion guide on Minikube  
https://chaos-mesh.org/docs/get_started/get_started_on_minikube/

## Installation
```
curl -sSL https://mirrors.chaos-mesh.org/v1.2.0/install.sh | bash
kubectl get pod -n chaos-testing
```

## Expose Chaos Dashboard
```
kubectl apply -f chaos-mesh-ingress.yaml
```

## Apply experiment file (sample)
```
kubectl apply -f experiments/network-delay.yaml
```

## Delete experiment file (sample)
```
kubectl delete -f experiments/network-delay.yaml
```

## Uninstallation
```
curl -sSL https://mirrors.chaos-mesh.org/v1.2.0/install.sh | bash -s -- --template | kubectl delete -f -
```
