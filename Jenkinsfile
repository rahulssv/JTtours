pipeline {
    agent any
 
    environment {
OPENSHIFT_SERVER = 'https://c100-e.us-south.containers.cloud.ibm.com:30954'
        OPENSHIFT_PROJECT = 'jttours'
        OPENSHIFT_TOKEN = 'sha256~PNReh2A2Nq3jSXJA9yKwOa9eT-zOKQ9Iswmy68z3bM'
        DOCKER_REGISTRY = 'docker.io'
        DOCKER_USERNAME = 'rahul1181'
        DOCKER_PASSWORD = 'R1hul@docker'
    }
 
    stages {
        stage('Authenticate OpenShift') {
            steps {
                script {
                    openshift.withCluster(server: OPENSHIFT_SERVER, token: OPENSHIFT_TOKEN) {
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
                    def buildAndPush = { dir, imageName ->
                        dir {
                            sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD} ${DOCKER_REGISTRY}"
                            sh "docker build -t ${DOCKER_USERNAME}/${imageName} ."
                            sh "docker push ${DOCKER_USERNAME}/${imageName}"
                            sh "docker logout ${DOCKER_REGISTRY}"
                        }
                    }
 
                    buildAndPush('./frontend', 'frontend-image:1.16')
                    buildAndPush('./backend', 'backend-image:1.3')
                }
            }
        }
 
        
    }
}