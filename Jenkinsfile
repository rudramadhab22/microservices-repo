pipeline {
    agent any

    environment {
        IMAGE_PREFIX = 'rudra1java'
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {

        stage('Checkout Code') {
            steps {
                echo "✅ Checking out code from Git..."
                git branch: 'main', url: 'https://github.com/rudramadhab22/microservices-repo.git'
            }
        }

        stage('Build GreetService') {
            steps {
                dir('GreetSevice') {
                    echo "🔨 Building GreetService..."
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build WelcomeService') {
            steps {
                dir('WelcomeServices') {
                    echo "🔨 Building WelcomeService..."
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build EurekaServer') {
            steps {
                dir('UrekaServer') {
                    echo "🔨 Building EurekaServer..."
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                echo "🚀 Building Docker images..."
                sh "docker-compose -f ${DOCKER_COMPOSE_FILE} build"
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    echo "🔑 Logging in to Docker Hub..."
                    docker.withRegistry('https://index.docker.io/v1/', 'docker-hub-cred') {
                        echo "📦 Pushing GreetService image..."
                        sh "docker tag greetsevice:latest ${IMAGE_PREFIX}/greetsevice:latest"
                        sh "docker push ${IMAGE_PREFIX}/greetsevice:latest"

                        echo "📦 Pushing WelcomeService image..."
                        sh "docker tag welcomeservices:latest ${IMAGE_PREFIX}/welcomeservices:latest"
                        sh "docker push ${IMAGE_PREFIX}/welcomeservices:latest"

                        echo "📦 Pushing EurekaServer image..."
                        sh "docker tag urekaserver:latest ${IMAGE_PREFIX}/urekaserver:latest"
                        sh "docker push ${IMAGE_PREFIX}/urekaserver:latest"
                    }
                }
            }
        }

        stage('Deploy Containers') {
            steps {
                echo "📦 Deploying containers with docker-compose..."
                sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
            }
        }

        stage('Verify Deployment') {
            steps {
                echo "✅ Listing running containers..."
                sh 'docker ps'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed. Check the logs for errors.'
        }
        always {
            echo 'Pipeline finished!'
        }
    }
}
