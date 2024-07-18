pipeline {
    agent any
    
    parameters {
        string(name: 'DOCKER_HUB_USERNAME', defaultValue: '', description: 'Docker Hub username')
        password(name: 'DOCKER_HUB_PASSWORD', defaultValue: '', description: 'Docker Hub password')
        string(name: 'IMAGE_NAME', defaultValue: '', description: 'Name of the Docker image')
        string(name: 'TAG', defaultValue: 'latest', description: 'Tag for the Docker image')
    }
    
    environment {
        DOCKER_REGISTRY = 'docker.io'
        DOCKERFILE_PATH = './Dockerfile'  // Path to your Dockerfile in the Jenkins workspace
    }
    
    stages {
        stage('Test the code with Docker') {
            agent {
                docker { image 'maven:3-openjdk-18' }
            }
            steps {
                sh 'mvn test'
            }
        }

        stage('Containerization') {
            steps {
                script {
                    // Containerize using the Dockerfile in the workspace
                    sh "docker build -t ${IMAGE_NAME}:${TAG} -f ${DOCKERFILE_PATH} ."
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Login to Docker Hub
                    withCredentials([usernamePassword(credentialsId: 'Docker-hub', passwordVariable: 'DOCKER_HUB_PASSWORD', usernameVariable: 'DOCKER_HUB_USERNAME')]) {
                        sh "docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}"
                    }
                    
                    // Tag the Docker image
                    sh "docker tag ${IMAGE_NAME}:${TAG} ${DOCKER_REGISTRY}/${DOCKER_HUB_USERNAME}/${IMAGE_NAME}:${TAG}"
                    
                    // Push the Docker image to Docker Hub
                    sh "docker push ${DOCKER_REGISTRY}/${DOCKER_HUB_USERNAME}/${IMAGE_NAME}:${TAG}"
                }
            }
        }
    }
}
