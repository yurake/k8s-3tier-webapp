# Installing Tekton Pipelines

<https://github.com/tektoncd/pipeline/blob/master/docs/install.md>

```sh
$ kubectl apply --filename https://storage.googleapis.com/tekton-releases/pipeline/latest/release.yaml
namespace/tekton-pipelines created
podsecuritypolicy.policy/tekton-pipelines created
clusterrole.rbac.authorization.k8s.io/tekton-pipelines-admin created
serviceaccount/tekton-pipelines-controller created
clusterrolebinding.rbac.authorization.k8s.io/tekton-pipelines-controller-admin created
customresourcedefinition.apiextensions.k8s.io/clustertasks.tekton.dev created
customresourcedefinition.apiextensions.k8s.io/conditions.tekton.dev created
customresourcedefinition.apiextensions.k8s.io/images.caching.internal.knative.dev created
customresourcedefinition.apiextensions.k8s.io/pipelines.tekton.dev created
customresourcedefinition.apiextensions.k8s.io/pipelineruns.tekton.dev created
customresourcedefinition.apiextensions.k8s.io/pipelineresources.tekton.dev created
customresourcedefinition.apiextensions.k8s.io/tasks.tekton.dev created
customresourcedefinition.apiextensions.k8s.io/taskruns.tekton.dev created
service/tekton-pipelines-controller created
service/tekton-pipelines-webhook created
clusterrole.rbac.authorization.k8s.io/tekton-aggregate-edit created
clusterrole.rbac.authorization.k8s.io/tekton-aggregate-view created
configmap/config-artifact-bucket created
configmap/config-artifact-pvc created
configmap/config-defaults created
configmap/config-logging created
configmap/config-observability created
deployment.apps/tekton-pipelines-controller created
deployment.apps/tekton-pipelines-webhook created

$ kubectl get pods --namespace tekton-pipelines
NAME                                          READY   STATUS    RESTARTS   AGE
tekton-pipelines-controller-8954886cc-h8gfd   1/1     Running   0          15s
tekton-pipelines-webhook-6c9bccbd6c-9cscr     1/1     Running   0          15s
```

## Hello World Tutorial

<https://github.com/tektoncd/pipeline/blob/master/docs/tutorial.md>

```bash
$ kubectl apply -f echo-hello-world.yaml
task.tekton.dev/echo-hello-world created

$ kubectl apply -f echo-hello-world-task-run.yaml
taskrun.tekton.dev/echo-hello-world-task-run created

$ kubectl tkn taskrun describe echo-hello-world-task-run
Name:        echo-hello-world-task-run
Namespace:   default
Task Ref:    echo-hello-world

🌡️  Status

STARTED         DURATION     STATUS
9 minutes ago   24 seconds   Succeeded

📨 Input Resources

No resources

📡 Output Resources

No resources

⚓ Params

No params

🦶 Steps

 NAME     STATUS
 ∙ echo   Completed

🚗 Sidecars

No sidecars

$ kubectl tkn taskrun logs echo-hello-world-task-run
[echo] {"level":"info","ts":1583067461.3041785,"logger":"fallback-logger","caller":"logging/config.go:69","msg":"Fetch GitHub commit ID from kodata failed: \"KO_DATA_PATH\" does not exist or is empty"}
[echo] hello world

$ kubectl get tekton-pipelines                                                                           (git)-[master]
NAME                               AGE
task.tekton.dev/echo-hello-world   13m

NAME                                           SUCCEEDED   REASON      STARTTIME   COMPLETIONTIME
taskrun.tekton.dev/echo-hello-world-task-run   True        Succeeded   12m         11m
```
