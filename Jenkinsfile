pipeline {
    agent any
    
    tools {
        maven "Maven"
    }

    triggers {
        pollSCM "* * * * *"
    }

    stages {

        stage('Build') {
            steps {
                echo 'Building SCS'
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing SCS'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying SCS'
            }
        }
    }
}