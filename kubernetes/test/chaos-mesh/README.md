# Chaos Mesh

## Reference
See installaion guide on Minikube  
https://chaos-mesh.org/docs/quick-start/

## Installation
```bash
curl -sSL https://mirrors.chaos-mesh.org/v2.4.2/install.sh | bash
kubectl get pod -n chaos-testing
```

## Expose Chaos Dashboard
```bash
kubectl apply -f chaos-mesh-namespace.yaml
kubectl apply -f chaos-mesh-ingress.yaml
```

## Apply experiment file (sample)
```bash
kubectl apply -f experiments/network-delay.yaml
```

## Check status
```bash
kubectl get schedule -n chaos-testing
```

## Delete experiment file (sample)
```bash
kubectl delete -f experiments/network-delay.yaml
```
