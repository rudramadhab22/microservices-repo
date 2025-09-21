pipeline {
    agent any

    environment {
        DOCKER_HUB_CRED = 'docker-hub-cred' // Your Jenkins Docker Hub credentials
        DOCKER_USERNAME = 'rudra1java'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/rudramadhab22/microservices-repo.git'
                sh 'ls -R' // Debug: verify workspace
            }
        }

        stage('Build Microservices') {
            parallel {
                stage('Build GreetService') {
                    steps {
                        dir('GreetSevice') {
                            sh 'chmod +x mvnw'
                            sh './mvnw clean package -DskipTests'
                            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                        }
                    }
                }
                stage('Build WelcomeService') {
                    steps {
                        dir('WelcomeServices') {
                            sh 'chmod +x mvnw'
                            sh './mvnw clean package -DskipTests'
                            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                        }
                    }
                }
                stage('Build EurekaServer') {
                    steps {
                        dir('UrekaServer') {
                            sh 'chmod +x mvnw'
                            sh './mvnw clean package -DskipTests'
                            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                echo "🚀 Building Docker images..."
                sh 'docker compose build'
            }
        }

        stage('Push Docker Images') {
            steps {
                echo "🔑 Logging in to Docker Hub..."
                withDockerRegistry([credentialsId: "${DOCKER_HUB_CRED}", url: "https://index.docker.io/v1/"]) {
                    sh 'docker compose push'
                }
            }
        }

        stage('Deploy Containers') {
            steps {
                echo "📦 Deploying containers..."
                sh 'docker compose up -d'
            }
        }

        stage('Verify Deployment') {
            steps {
                echo "✅ Deployment complete! Verify your services."
            }
        }
    }

    post {
        always {
            echo "Pipeline finished."
        }
        success {
            echo "🎉 Pipeline succeeded!"
        }
        failure {
            echo "❌ Pipeline failed!"
        }
    }
}
