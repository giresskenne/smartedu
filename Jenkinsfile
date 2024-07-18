pipeline {
    agent any
    
    parameters {
        string(name: 'DOCKER_HUB_USERNAME', defaultValue: '', description: 'Docker Hub username')
        password(name: 'DOCKER_HUB_PASSWORD', defaultValue: '', description: 'Docker Hub password')
        string(name: 'IMAGE_NAME', defaultValue: 'my-web-app', description: 'Name of the Docker image')
        string(name: 'TAG', defaultValue: 'latest', description: 'Tag for the Docker image')
    }
    
    environment {
        DOCKER_REGISTRY = 'docker.io'
        TEST_IMAGE = 'node:lts'  // Example Node.js image for testing
    }
    
    stages {
        stage('Test the code with Docker') {
            agent {
                docker { image 'maven:3-openjdk-18' }
            }
            steps {
                sh '''
                mvn test
                '''
            }
        }

        stage('contenarization') {
            steps{
                script {
                    // Contanirization using the script into the Dockerfile
                    sh "./Dockerfile"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Login to Docker Hub
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKER_HUB_PASSWORD', usernameVariable: 'DOCKER_HUB_USERNAME')]) {
                        sh "docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}"
                    }
                    
                    // Build the Docker image
                    sh "docker build -t ${DOCKER_REGISTRY}/${DOCKER_HUB_USERNAME}/${IMAGE_NAME}:${TAG} ."
                }
            }
        }
        
        // stage('Test in Docker Container') {
        //     steps {
        //         script {
        //             // Run tests inside a Docker container
        //             docker.image(TEST_IMAGE).inside {
        //                 // Example: Run npm test inside the container
        //                 sh "npm install"
        //                 sh "npm test"
        //             }
        //         }
        //     }
        // }
        
        stage('Push to Docker Hub') {
            steps {
                script {
                    // Push the Docker image to Docker Hub
                    sh "docker push ${DOCKER_REGISTRY}/${DOCKER_HUB_USERNAME}/${IMAGE_NAME}:${TAG}"
                }
            }
        }
    }
}