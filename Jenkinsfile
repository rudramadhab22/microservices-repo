pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
        DOCKER_HUB_CREDENTIAL = 'docker-hub-cred'
        DOCKER_HUB_ACCOUNT = 'rudra1java'
    }

    stages {
        stage('Checkout') {
            steps {
                echo "✅ Checking out code from Git..."
                git branch: 'main', url: 'https://github.com/rudramadhab22/microservices-repo.git'
                sh 'ls -R' // Debug: list all files
            }
        }

        stage('Build GreetService') {
            steps {
                dir('GreetSevice') {
                    echo "🔨 Building GreetService..."
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build WelcomeService') {
            steps {
                dir('WelcomeServices') {
                    echo "🔨 Building WelcomeService..."
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build EurekaServer') {
            steps {
                dir('UrekaServer') {
                    echo "🔨 Building EurekaServer..."
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    echo "🚀 Building Docker images..."
                    sh "docker compose -f ${DOCKER_COMPOSE_FILE} build"
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    echo "🔑 Logging in to Docker Hub..."
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_HUB_CREDENTIAL}") {
                        echo "Pushing GreetService..."
                        sh "docker tag greetsevice:latest ${DOCKER_HUB_ACCOUNT}/greetservice:latest"
                        sh "docker push ${DOCKER_HUB_ACCOUNT}/greetservice:latest"

                        echo "Pushing WelcomeService..."
                        sh "docker tag welcome-service:latest ${DOCKER_HUB_ACCOUNT}/welcomeservices:latest"
                        sh "docker push ${DOCKER_HUB_ACCOUNT}/welcomeservices:latest"

                        echo "Pushing EurekaServer..."
                        sh "docker tag eureka-server:latest ${DOCKER_HUB_ACCOUNT}/eurekaserver:latest"
                        sh "docker push ${DOCKER_HUB_ACCOUNT}/eurekaserver:latest"
                    }
                }
            }
        }

        stage('Deploy Containers') {
            steps {
                script {
                    echo "🚀 Deploying containers..."
                    sh "docker compose -f ${DOCKER_COMPOSE_FILE} up -d"
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    echo "📦 Listing running containers..."
                    sh "docker ps"
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed! Check logs.'
        }
        always {
            echo 'Pipeline finished.'
        }
    }
}
