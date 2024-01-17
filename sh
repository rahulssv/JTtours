#!/bin/bash
oc create ns jttours

# For MySQL Database
oc apply -f kubernetes/mysql/mysql-pv.yaml -n jttours
oc apply -f kubernetes/mysql/mysql-secret.yaml -n jttours
oc apply -f kubernetes/mysql/mysql-configmap.yaml -n jttours
oc apply -f kubernetes/mysql/mysql-statefulset.yaml -n jttours
oc apply -f kubernetes/mysql/mysql-service.yaml -n jttours

# For Backend
oc create configmap backend-configmap \
  --from-literal=SPRING_DATASOURCE_URL="jdbc:mysql://$(oc get svc mysql-service -n jttours -o jsonpath='{.spec.clusterIP}')/jttoursdb" \
  --from-literal=SPRING_DATASOURCE_USERNAME="root" \
  --from-literal=SPRING_DATASOURCE_PASSWORD="admin" \
  -n jttours

oc apply -f kubernetes/backend/backend-deployment.yaml -n jttours
oc apply -f kubernetes/backend/backend-service.yaml -n jttours
oc expose svc backend-service -n jttours


# For frontend
oc create configmap frontend-configmap \
  --from-literal=REACT_APP_API_URL=$(oc get route backend-service -n jttours -o jsonpath='{.spec.host}')\
  -n jttours

oc apply -f kubernetes/frontend/frontend-deployment.yaml -n jttours
oc apply -f kubernetes/frontend/frontend-service.yaml -n jttours
oc expose svc frontend-service -n jttours

oc get all -n jttours

echo http://$(oc get route frontend-service -n jttours -o jsonpath='{.spec.host}')