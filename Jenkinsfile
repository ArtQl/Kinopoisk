pipeline {
    agent any
    tools {
        maven 'Maven3' // Указываем Maven из Global Tool Configuration
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build and Test') {
            steps {
                sh 'mvn clean install' // Выполняет сборку и тесты
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml' // Более надежный путь с ** для поиска
                }
            }
        }
    }
}