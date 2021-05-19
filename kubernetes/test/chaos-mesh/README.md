# Chaos Mesh

## Reference
See installaion guide on Minikube  
https://chaos-mesh.org/docs/get_started/get_started_on_minikube/

## Installation
```bash
curl -sSL https://mirrors.chaos-mesh.org/v1.2.0/install.sh | bash
kubectl get pod -n chaos-testing
```

## Expose Chaos Dashboard
```bash
kubectl apply -f chaos-mesh-ingress.yaml
```

## Apply experiment file (sample)
```bash
kubectl apply -f experiments/network-delay.yaml
```

## Delete experiment file (sample)
```bash
kubectl delete -f experiments/network-delay.yaml
```

## Uninstallation
```bash
curl -sSL https://mirrors.chaos-mesh.org/v1.2.0/install.sh | bash -s -- --template | kubectl delete -f -
```
