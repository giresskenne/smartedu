pipeline {
    agent any

    environment {
        // Define environment variables without command substitution
        DOCKER_VERSION = '5:20.10.24~3-0~ubuntu'
    }

    // parameters {
        // Define parameters
        // string(name: 'BRANCH', defaultValue: 'main', description: 'Branch to build')
        // choices(name: 'VERSION', defaultValue: 'main', description: 'Branch to build')
        // booleanParam(name: 'RUN_TESTS', defaultValue: true, description: 'Run tests')
        // choice(name: 'DEPLOY_ENV', choices: ['development', 'staging', 'production'], description: 'Deployment environment')
    // }

    stages {
        stage('Installing Docker') {
            steps {
                script {
                    // Set up Docker's apt repository
                    sh '''
                    cd /home/ubuntu
                    # Add Docker's official GPG key:
                    sudo apt-get update
                    sudo apt-get install -y ca-certificates curl
                    sudo install -m 0755 -d /etc/apt/keyrings
                    sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
                    sudo chmod a+r /etc/apt/keyrings/docker.asc

                    # Add the repository to Apt sources:
                    echo \
                      "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
                      $(. /etc/os-release && echo \"$VERSION_CODENAME\") stable" | \
                      sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
                    sudo apt-get update
                    '''
                    
                    // Install the Docker packages
                    sh '''
                    cd /home/ubuntu
                    VERSION_STRING=5:20.10.24~3-0~ubuntu-$(lsb_release -cs)
                    sudo apt-get install -y docker-ce=$VERSION_STRING docker-ce-cli=$VERSION_STRING containerd.io docker-buildx-plugin docker-compose-plugin
                    '''

                    // Verify that the Docker Engine installation is successful by running the hello-world image
                    sh '''
                    cd /home/ubuntu
                    docker --version
                    sudo docker run hello-world
                    '''
                }
            }
        }
    }
}
