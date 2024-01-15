pipeline {
    agent any
 
    environment {
        OPENSHIFT_CRED_ID = '1c5a682d-38da-41e0-9ad1-468811c16bc4'
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
        }
 
        stage('Deploy to OpenShift') {
            steps {
                script {
                    openshift.withCluster(credentialsId: OPENSHIFT_CRED_ID, server: OPENSHIFT_SERVER) {
                        openshift.withProject(OPENSHIFT_PROJECT) {
                            sh './sh'
                        }
                    }
                }
            }
        }
    }
}
