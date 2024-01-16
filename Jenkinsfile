pipeline {
    agent any
 
    environment {
OPENSHIFT_SERVER = 'https://c100-e.us-south.containers.cloud.ibm.com:30954'
        OPENSHIFT_PROJECT = 'jttours'
        OPENSHIFT_CREDENTIALS_ID = '1c5a682d-38da-41e0-9ad1-468811c16bc4'
        DOCKER_REGISTRY = 'docker.io'
        DOCKER_CREDENTIALS_ID = '905152c7-643b-4416-94eb-9cbb2e5f40d9'
    }
 
    stages {
        stage('Authenticate OpenShift') {
            steps {
                script {
                    withCredentials([string(credentialsId: OPENSHIFT_CREDENTIALS_ID, variable: 'OPENSHIFT_TOKEN')]) {
                        openshift.withCluster(server: OPENSHIFT_SERVER, token: OPENSHIFT_TOKEN) {
                            openshift.withProject(OPENSHIFT_PROJECT) {
                                echo "Successfully authenticated with OpenShift"
                            }
                        }
                    }
                }
            }
        }
 
        stage('Build and Push Docker Images') {
            steps {
                script {
                    def buildAndPush = { dir, imageName ->
                        dir {
                            withCredentials([usernamePassword(credentialsId: DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                                sh "docker login -u \${DOCKER_USERNAME} -p \${DOCKER_PASSWORD} \${DOCKER_REGISTRY}"
                                sh "docker build -t \${DOCKER_USERNAME}/\${imageName} ."
                                sh "docker push \${DOCKER_USERNAME}/\${imageName}"
                                sh "docker logout \${DOCKER_REGISTRY}"
                            }
                        }
                    }
 
                    buildAndPush('./frontend', 'frontend-image:1.15')
                    buildAndPush('./backend', 'backend-image:1.2')
                }
            }
        }
 
        stage('Deploy to OpenShift') {
            steps {
                script {
                    withCredentials([string(credentialsId: OPENSHIFT_CREDENTIALS_ID, variable: 'OPENSHIFT_TOKEN')]) {
                        openshift.withCluster(server: OPENSHIFT_SERVER, token: OPENSHIFT_TOKEN) {
                            openshift.withProject(OPENSHIFT_PROJECT) {
                                sh "./sh"
                            }
                        }
                    }
                }
            }
        }
    }
 
    post {
        always {
            cleanWs()
        }
    }
}