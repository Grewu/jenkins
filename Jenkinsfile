pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
               echo 'Building...'
               bat 'chmod +x gradlew'
               bat './gradlew build'
            }
        }
    }
}
