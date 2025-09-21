pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "✅ Checking out code from Git..."
                git branch: 'main', url: 'https://github.com/rudramadhab22/microservices-repo.git'

            }
        }

        stage('Build Microservices JARs') {
            steps {
                echo "🔨 Building all microservices using Maven..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Images & Deploy') {
            steps {
                script {
                    echo "🛑 Stopping any running containers..."
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"

                    echo "🚀 Building Docker images and starting containers..."
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    echo "📦 Listing running containers..."
                    sh 'docker ps'
                }
            }
        }
    }

    post {
        success {
            echo '✅ Deployment successful!'
        }
        failure {
            echo '❌ Deployment failed!'
        }
        always {
            echo 'Pipeline finished!'
        }
    }
}
