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
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build WelcomeServices') {
            steps {
                dir('microservices-repo/WelcomeServices') {
                    echo "🔨 Building WelcomeServices..."
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build UrekaServer') {
            steps {
                dir('microservices-repo/UrekaServer') {
                    echo "🔨 Building UrekaServer..."
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    echo "🚀 Building Docker images..."
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} build"
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    echo "🔑 Logging in to Docker Hub..."
                    docker.withRegistry('https://index.docker.io/v1/', 'docker-hub-cred') {
                        def services = ['GreetSevice', 'WelcomeServices', 'UrekaServer']
                        services.each { svc ->
                            def imageName = "rudramadhab22/${svc.toLowerCase()}:latest"
                            echo "Pushing image: ${imageName}"
                            sh "docker tag microservices-repo_${svc.toLowerCase()} $imageName"
                            sh "docker push $imageName"
                        }
                    }
                }
            }
        }

        stage('Deploy Containers') {
            steps {
                script {
                    echo "🛑 Stopping any running containers..."
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"

                    echo "🚀 Starting containers with Docker Compose..."
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
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
