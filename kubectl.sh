#!/bin/bash
kubectl create ns jttours

# For MySQL Database
kubectl apply -f kubernetes/mysql/mysql-pv.yaml -n jttours
kubectl apply -f kubernetes/mysql/mysql-secret.yaml -n jttours
kubectl apply -f kubernetes/mysql/mysql-configmap.yaml -n jttours
kubectl apply -f kubernetes/mysql/mysql-statefulset.yaml -n jttours
kubectl apply -f kubernetes/mysql/mysql-service.yaml -n jttours

# For Backend
kubectl create configmap backend-configmap \
  --from-literal=SPRING_DATASOURCE_URL="jdbc:mysql://$(kubectl get svc mysql-service -n jttours -o jsonpath='{.spec.clusterIP}')/jttoursdb" \
  --from-literal=SPRING_DATASOURCE_USERNAME="root" \
  --from-literal=SPRING_DATASOURCE_PASSWORD="admin" \
  -n jttours

kubectl create secret docker-registry my-registry-secret \
    --docker-server=docker.io \
    --docker-username=rahul1181 \
    --docker-password=********* \
    --docker-email=rahulvishwakarma1181@gmail.com \
    -n jttours

kubectl apply -f kubernetes/backend/backend-deployment.yaml -n jttours
kubectl apply -f kubernetes/backend/backend-service.yaml -n jttours
kubectl expose svc backend-service -n jttours


# For frontend
kubectl create configmap frontend-configmap \
  --from-literal=REACT_APP_API_URL=$(kubectl get route backend-service -n jttours -o jsonpath='{.spec.host}')\
  -n jttours

kubectl apply -f kubernetes/frontend/frontend-deployment.yaml -n jttours
kubectl apply -f kubernetes/frontend/frontend-service.yaml -n jttours
kubectl expose svc frontend-service -n jttours

kubectl get all -n jttours

echo http://$(kubectl get route frontend-service -n jttours -o jsonpath='{.spec.host}')
