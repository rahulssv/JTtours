pipeline {
    agent any
 
    environment {
OPENSHIFT_SERVER = 'https://c100-e.us-south.containers.cloud.ibm.com:30954'
        OPENSHIFT_PROJECT = 'jttours'
        OPENSHIFT_CREDENTIALS_ID = '1c5a682d-38da-41e0-9ad1-468811c16bc4'
        DOCKER_REGISTRY = 'rahul1181'
        DOCKER_CREDENTIALS_ID = '905152c7-643b-4416-94eb-9cbb2e5f40d9'
    }
 
    stages {
        
 
        stage('Build and Push Docker Images - Frontend') {
            steps {
                script {
                    dir('./frontend') {
                        withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                            sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                            sh "docker build -t ${DOCKER_REGISTRY}/frontend-image:1.15 ."
                            sh "docker push ${DOCKER_REGISTRY}/frontend-image:1.15"
                            sh "docker logout ${DOCKER_REGISTRY}"
                        }
                    }
                }
            }
        }
 
        stage('Build and Push Docker Images - Backend') {
            steps {
                script {
                    dir('./backend') {
                        withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                            sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                            sh "docker build -t ${DOCKER_REGISTRY}/backend-image:1.2 ."
                            sh "docker push ${DOCKER_REGISTRY}/backend-image:1.2"
                            sh "docker logout ${DOCKER_REGISTRY}"
                        }
                    }
                }
            }
        }
 
        stage('Deploy to OpenShift') {
            steps {
                script {
                    withCredentials([string(credentialsId: OPENSHIFT_CREDENTIALS_ID, variable: 'OPENSHIFT_TOKEN')]) {
                        openshift.withCluster(server: OPENSHIFT_SERVER, token: OPENSHIFT_TOKEN) {
                            openshift.withProject(OPENSHIFT_PROJECT) {
                                // Customize the OpenShift deployment commands based on your project
                                sh './sh'
                            }
                        }
                    }
                }
            }
        }
    }
}