pipeline {
    agent any
 
    environment {
        OPENSHIFT_PROJECT = 'jttours'
        KUBE_CRED_ID = 'your-kube-credentials-id'
    }
 
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
 
        stage('Build and Push Docker Images') {
            steps {
                script {
docker.build("rahul1181/frontend-image:1.15", "./frontend")
docker.build("rahul1181/backend-image:1.2", "./backend")
docker.withRegistry('https://hub.docker.com', 'rahul1181') {
                        docker.image("rahul1181/frontend-image:1.16").push()
                        docker.image("rahul1181/backend-image:1.3").push()
                    }
                }
            }
        }
 
        stage('Deploy to OpenShift') {
            steps {
                script {
                    // Deploy MySQL StatefulSet, ConfigMap, Service, and Route
                    sh 'oc apply -f kubernetes/mysql/mysql-statefulset.yaml -n $OPENSHIFT_PROJECT'
                    sh 'oc apply -f kubernetes/mysql/mysql-configmap.yaml -n $OPENSHIFT_PROJECT'
                    sh 'oc apply -f kubernetes/mysql/mysql-service.yaml -n $OPENSHIFT_PROJECT'
                    sh 'oc apply -f kubernetes/mysql/mysql-route.yaml -n $OPENSHIFT_PROJECT'
 
                    // Deploy frontend and backend Deployments, Services, and Routes
                    sh 'oc apply -f kubernetes/frontend/frontend-deployment.yaml -n $OPENSHIFT_PROJECT'
                    sh 'oc apply -f kubernetes/frontend/frontend-service.yaml -n $OPENSHIFT_PROJECT'
                    sh 'oc apply -f kubernetes/frontend/frontend-route.yaml -n $OPENSHIFT_PROJECT'
 
                    sh 'oc apply -f kubernetes/backend/backend-statefulset.yaml -n $OPENSHIFT_PROJECT'
                    sh 'oc apply -f kubernetes/backend/backend-service.yaml -n $OPENSHIFT_PROJECT'
                    sh 'oc apply -f kubernetes/backend/backend-route.yaml -n $OPENSHIFT_PROJECT'
                }
            }
        }
    }
}