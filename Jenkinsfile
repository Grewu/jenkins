pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'chmod +x gradlew'
                sh './gradlew build'
            }
        }
    }
}
