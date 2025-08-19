# learnopedia-app-backend
learnopedia backend



## Docker build
config-server
```
cd config-server
mvn clean package -DskipTests
docker build -t macwlu/learnopedia-app:config-server .
```

eureka-server
```
cd ../eureka-server
mvn clean package -DskipTests
docker build -t macwlu/learnopedia-app:eureka-server .
```

api-gateway
```
cd ../api-gateway
mvn clean package -DskipTests
docker build -t macwlu/learnopedia-app:api-gateway .
```

service1
```
cd ../service1
mvn clean package -DskipTests
docker build -t macwlu/learnopedia-app:service1 .
```

service2
```
cd ../service2
mvn clean package -DskipTests
docker build -t macwlu/learnopedia-app:service2 .
```

### Docker Auth
```bash
docker login
```

### Docker push to private repo

```bash
docker push macwlu/learnopedia-app:config-server
docker push macwlu/learnopedia-app:eureka-server
docker push macwlu/learnopedia-app:api-gateway
docker push macwlu/learnopedia-app:service1
docker push macwlu/learnopedia-app:service2
```

## Kubernetes

minikube
```bash
brew install minikube
minikube version
minikube start
kubectl config use-context minikube

```
cluster-info
```bash
kubectl cluster-info
kubectl config view

```

if you have more than one context then select correct:
```bash

kubectl config get-contexts
kubectl config use-context <context-name>
```

if you have local api kubernetes then set env KUBECONFIG
```bash
export KUBECONFIG=~/.kube/config

```

docker secret key
```bash
kubectl create secret docker-registry my-registry-secret \
  --docker-server=https://index.docker.io/v1/ \
  --docker-username=<your-docker-username> \
  --docker-password=<your-docker-password> \
  --docker-email=<your-email> 

```

git credential token
```bash
kubectl create secret generic git-credentials \
  --from-literal=username=<your-git-username> \
  --from-literal=password=<your-git-password-or-token>
```


kubectl apply
```bash
kubectl apply -f k8s/config-server-deployment.yaml
kubectl apply -f k8s/eureka-server-deployment.yaml
kubectl apply -f k8s/api-gateway-deployment.yaml
kubectl apply -f k8s/service1-deployment.yaml
kubectl apply -f k8s/service2-deployment.yaml

```
kubectl rollout restart deployment config-server..

### Kubernetes testing
check
```bash
kubectl get pods
kubectl get services

```
testing API via port-forwarding:
```bash
kubectl port-forward service/api-gateway 8080:8080
curl http://localhost:8080/service1/endpoint

```