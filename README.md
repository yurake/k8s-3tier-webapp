# k8s-3tier-webapp

## About
Kubernetesでアプリが稼働するための定義

## 前提
以下CLIが導入されていること
* docker
* kubectl

## 稼働確認
### dockerイメージをビルド
`docker build`で実行する

```
docker build -t mycluster.icp:8500/default/docker_hub_id/wlp:v0.0.1 ./
```

### ビルドしたイメージをDocker Registryへプッシュ
`docker push`で実行する

```
docker push mycluster.icp:8500/default/docker_hub_id/wlp:v0.0.1
```

### ローカルでコンテナ起動
`docker run`で実行する

```
docker run --rm -d --name wlp -p 80:9080 -p 443:9443 --network oss-3tier-webapp mycluster.icp:8500/default/docker_hub_id/wlp:v0.0.1
```

### Kubernetes上でコンテナ稼働
`kubectl apply`で実行する

```
kubectl apply -f wlp-deployment.yaml
```
