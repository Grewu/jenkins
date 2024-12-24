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
    }
}
