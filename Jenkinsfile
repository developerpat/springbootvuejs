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
                sh 'mvn clean install -DskipTests'
            }
        }
        
        stage('First Teststage') {
            parallel{
                stage('Unit Testing'){
                    steps {
                        echo 'Unit Testing the SCS'
                        sh 'mvn clean test'
                    }
                }

                stage('Integration Testing'){
                    steps {
                        echo 'Integration Testing the SCS'
                        sh 'mvn clean integration-test'
                    }
                }

                stage('UI Testing'){
                    steps {
                        echo 'UI Testing the SCS'
                    }
                }
            }
        }

        stage('Sonarqube') {
            environment {
                scannerHome = tool 'SonarQubeScanner'
            }
    
            steps {
                withSonarQubeEnv('Sonarqube') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
                
                timeout(time: 10, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deployment into Nexus') {
            steps {
                echo 'Deploying SCS-Artefakt into Nexus'
            }
        }

        stage('Deployment into Testenvironment'){
            steps {
                echo 'Deploying SCS-Artefakt into testenvironment'
            }
        }

        stage('Smoke Test'){
            steps{
                echo 'Smoke-test'
            }
        }

        stage('Security Test'){
            steps{
                echo "Security Test"
            }

        }
    }
}