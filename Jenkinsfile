pipeline {
    agent any
 
    environment {
        OPENSHIFT_SERVER = 'https://c100-e.us-south.containers.cloud.ibm.com:30954'
        OPENSHIFT_PROJECT = 'jttours'
        OPENSHIFT_TOKEN_CREDENTIALS_ID = 'OPENSHIFT_ID'
        DOCKER_HUB_CREDENTIALS = 'DOCKER_ID'
        FRONTEND_IMAGE_NAME = 'rahul1181/frontend-image:latest'
        BACKEND_IMAGE_NAME = 'rahul1181/backend-image:latest'
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
        stage('Build and Push Images') {
            parallel {
                stage('Frontend') {
                    steps {
                        script {
                            
                            sh "docker build -t ${FRONTEND_IMAGE_NAME} ./frontend"
                           
                            dockerLoginAndPush(FRONTEND_IMAGE_NAME)
                        }
                    }
                }
                stage('Backend') {
                    steps {
                        script {
                            
                            sh "docker build -t ${BACKEND_IMAGE_NAME} ./backend"
                            
                            dockerLoginAndPush(BACKEND_IMAGE_NAME)
                        }
                    }
                }
            }
        }
        stage('Deploy to OpenShift') {
            steps {
                script {
                    
                    sh "./sh"
                }
            }
        }
 
        
    }
 
    post {
        always {
            
            script{
                echo "Always block executed!"
            }
        }
    
    }

}

def dockerLoginAndPush(imageName) {
        
        withCredentials([usernamePassword(credentialsId: DOCKER_HUB_CREDENTIALS, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
            sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
            sh "docker push ${imageName}"
        }
}