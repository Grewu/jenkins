pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
               echo 'Building...'
               bat 'gradlew.bat build'
            }
        }
    }
}
