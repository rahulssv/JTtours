pipeline {
    agent any
 
    environment {
        OPENSHIFT_SERVER = 'https://c100-e.us-south.containers.cloud.ibm.com:30954'
        OPENSHIFT_PROJECT = 'jttours'
        OPENSHIFT_TOKEN_CREDENTIALS_ID = 'OPENSHIFT_ID'
        DOCKER_HUB_CREDENTIALS = 'DOCKER_ID'
        FRONTEND_IMAGE_NAME = 'rahul1181/frontend-image:1.16'
        BACKEND_IMAGE_NAME = 'rahul1181/backend-image:1.3'
    }
 
    stages {
        stage('Authenticate to OpenShift') {
            steps {
                script {
                    withCredentials([string(credentialsId: OPENSHIFT_TOKEN_CREDENTIALS_ID, variable: 'OPENSHIFT_TOKEN')]) {
                        sh "oc login --token=${OPENSHIFT_TOKEN} ${OPENSHIFT_SERVER}"
                    }
                }
            }
        }
 
        
    }
 
    post {
        always {
            // Cleanup or additional steps after the pipeline
            script{
                echo "Always block executed!"
            }
        }
    
    }

}

def dockerLoginAndPush(imageName) {
        // Docker login to Docker Hub
        withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
            sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
            sh "docker push ${imageName}"
        }
}