pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "✅ Checking out code..."
                git branch: 'main', url: 'https://github.com/rudramadhab22/microservices-repo.git'
            }
        }

        stage('Build GreetSevice') {
            steps {
                dir('GreetSevice') {
                    echo "🔨 Building GreetSevice..."
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build WelcomeServices') {
            steps {
                dir('WelcomeServices') {
                    echo "🔨 Building WelcomeServices..."
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build UrekaServer') {
            steps {
                dir('UrekaServer') {
                    echo "🔨 Building UrekaServer..."
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build & Deploy Docker Images') {
            steps {
                script {
                    echo "🚀 Building Docker images and starting containers..."
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    echo "🔑 Logging in to Docker Hub..."
                    docker.withRegistry('https://index.docker.io/v1/', 'docker-hub-cred') {
                        sh 'docker push rudramadhab22/greetsevice:latest'
                        sh 'docker push rudramadhab22/welcomeservices:latest'
                        sh 'docker push rudramadhab22/urekaserver:latest'
                    }
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
            echo '✅ Pipeline succeeded!'
        }
        failure {
            echo '❌ Pipeline failed! Check logs.'
        }
        always {
            echo 'Pipeline finished.'
        }
    }
}
