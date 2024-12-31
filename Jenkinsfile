pipeline {
    agent any

    stages {
        stage('Publish') {
            steps {
                echo 'Publishing...'
                bat 'gradlew.bat publish'
            }
        }
        stage('Check Dependencies') {
            steps {
                echo 'Checking dependencies...'
                bat 'gradlew.bat dependencies'
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                bat 'gradlew.bat build'
            }
        }
    }
}
