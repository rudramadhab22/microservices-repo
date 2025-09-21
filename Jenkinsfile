pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_FILE = 'microservices-repo/docker-compose.yml'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "✅ Checking out code from GitHub..."
                git branch: 'main', url: 'https://github.com/rudramadhab22/microservices-repo.git'
            }
        }

        stage('Debug Workspace') {
            steps {
                echo "🔍 Debug: Listing all files in workspace..."
                sh 'ls -R'
            }
        }

        stage('Build GreetService') {
            steps {
                echo "🔨 Building GreetService..."
                dir('microservices-repo/GreetService') {
                    sh 'mvn clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build WelcomeService') {
            steps {
                echo "🔨 Building WelcomeService..."
                dir('microservices-repo/WelcomeService') {
                    sh 'mvn clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build EurekaServer') {
            steps {
                echo "🔨 Building EurekaServer..."
                dir('microservices-repo/EurekaServer') {
                    sh 'mvn clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
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
