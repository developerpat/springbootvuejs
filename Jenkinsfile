pipeline {
    agent any
    
    tools {
        maven "Maven"
    }

    triggers {
        pollSCM "* * * * *"
    }

    environment{
        IMAGE = readMavenPom().getArtifactId()
        VERSION = readMavenPom().getVersion()

        
        // This can be nexus3 or nexus2
        NEXUS_VERSION = "nexus3"
        // This can be http or https
        NEXUS_PROTOCOL = "http"
        // Where your Nexus is running
        NEXUS_URL = "pentasys_devops_nexus_1:8081"
        // Jenkins credential id to authenticate to Nexus OSS
        NEXUS_CREDENTIAL_ID = "nexus-credentials"
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

        stage ('Dockerbuild'){
            steps {
                echo "Dockerbuild"
                echo "Imagename: ${IMAGE}"
                echo "Imageversion: ${VERSION}"
                sh "docker build -t 127.0.0.1:8123/${IMAGE}:${VERSION} ."
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