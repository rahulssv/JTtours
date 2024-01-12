pipeline {
    agent any
 
    environment {
        OPENSHIFT_CRED_ID = ''
OPENSHIFT_SERVER = 'https://c100-e.us-south.containers.cloud.ibm.com:30954'
        OPENSHIFT_PROJECT = 'jttours'
    }
 
    stages {
        stage('Authenticate with OpenShift') {
            steps {
                script {
                    openshift.withCluster(credentialsId: OPENSHIFT_CRED_ID, server: OPENSHIFT_SERVER) {
                        openshift.withProject(OPENSHIFT_PROJECT) {
                            echo "Successfully authenticated with OpenShift"
                        }
                    }
                }
            }
        }
 
        stage('Build and Push Docker Images') {
            steps {
                script {
docker.build("rahul1181/frontend-image:1.15", "./frontend")
docker.build("rahul1181/backend-image:1.2", "./backend")
docker.withRegistry('https://hub.docker.com/', 'rahul1181') {
                        docker.image("rahul1181/frontend-image:1.15").push()
                        docker.image("rahul1181/backend-image:1.2").push()
                    }
            }
        }
 
        stage('Deploy to OpenShift') {
            steps {
                script {
                    openshift.withCluster(credentialsId: OPENSHIFT_CRED_ID, server: OPENSHIFT_SERVER) {
                        openshift.withProject(OPENSHIFT_PROJECT) {
                            sh 'oc apply -f kubernetes/mysql/mysql-pv.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/mysql/mysql-secret.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/mysql/mysql-configmap.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/mysql/mysql-statefulset.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/mysql/mysql-service.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/mysql/mysql-route.yaml -n $OPENSHIFT_PROJECT'
        
                            sh 'oc apply -f kubernetes/backend/backend-configmap.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/backend/backend-deployment.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/backend/backend-service.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/backend/backend-route.yaml -n $OPENSHIFT_PROJECT'

                            // Deploy frontend and backend Deployments, Services, and Routes
                            sh 'oc apply -f kubernetes/frontend/frontend-configmap.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/frontend/frontend-deployment.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/frontend/frontend-service.yaml -n $OPENSHIFT_PROJECT'
                            sh 'oc apply -f kubernetes/frontend/frontend-route.yaml -n $OPENSHIFT_PROJECT'
                        }
                    }
                }
            }
        }
    }
}