pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                // Выполнение команды Gradle для сборки проекта
                sh 'chmod +x gradlew'
                sh './gradlew build'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                // Выполнение команды Gradle для тестирования проекта
                sh './gradlew test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
                // Добавьте команды для деплоя вашего проекта
                sh 'echo Deploy step'
            }
        }
    }
}
