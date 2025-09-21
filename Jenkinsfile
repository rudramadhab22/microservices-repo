pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_FILE = 'microservices-repo/docker-compose.yml'
    }

    stages {

        stage('Debug Workspace') {
            steps {
                echo "🔍 Debug: Listing all files in workspace..."
                sh 'pwd'
                sh 'ls -R'
            }
        }

        stage('Checkout Code') {
            steps {
                echo "✅ Checking out code from GitHub..."
                git branch: 'main', url: 'https://github.com/rudramadhab22/microservices-repo.git'
            }
        }

        stage('Build GreetSevice') {
            steps {
                dir('microservices-repo/GreetSevice') {
                    echo "🔨 Building GreetSevice..."
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build WelcomeServices') {
            steps {
                dir('microservices-repo/WelcomeServices') {
                    echo "🔨 Building WelcomeServices..."
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build UrekaServer') {
            steps {
                dir('microservices-repo/UrekaServer') {
                    echo "🔨 Building UrekaServer..."
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Docker Compose Deploy') {
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
                echo "📦 Listing running Docker containers..."
                sh 'docker ps'
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed. Check logs for errors."
        }
        always {
            echo "Pipeline finished."
        }
    }
}
