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
        GROUPID = readMavenPom().getGroupId()

        
        // This can be nexus3 or nexus2
        NEXUS_VERSION = "nexus3"
        // This can be http or https
        NEXUS_PROTOCOL = "http"
        // Where your Nexus is running
        NEXUS_URL = "pentasysnexus:8081"
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
                sh "docker build -t 127.0.0.1:8123/${GROUPID}/${IMAGE}:${VERSION} ."
            }
        }

        stage('Publish to Nexus') {
            parallel{
                stage('Publish Dockerimage'){
                    steps{  
                        sh "docker push 127.0.0.1:8123/${GROUPID}/${IMAGE}:${VERSION}"
                        }
                    }

                stage('Publish Artifacts'){
                    steps{
                        script{
                            // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                            pom = readMavenPom file: "backend/pom.xml";
                            // Find built artifact under target folder
                            filesByGlob = findFiles(glob: "backend/target/*.${pom.packaging}");
                            // Print some info from the artifact found
                            echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                            // Extract the path from the File found
                            artifactPath = filesByGlob[0].path;
                            // Assign to a boolean response verifying If the artifact name exists
                            artifactExists = fileExists artifactPath;
                            if(env.BRANCH_NAME != 'master'){
                                MVN_REPOSITORY='maven-snapshots'
                            }else{
                                MVN_REPOSITORY='maven-releases'
                            }
                            if(artifactExists) {
                                echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                                nexusArtifactUploader(
                                    nexusVersion: NEXUS_VERSION,
                                    protocol: NEXUS_PROTOCOL,
                                    nexusUrl: NEXUS_URL,
                                    groupId: pom.groupId,
                                    version: VERSION,
                                    repository: MVN_REPOSITORY,
                                    credentialsId: 'jenkins_nexus',
                                    artifacts: [
                                        // Artifact generated such as .jar, .ear and .war files.
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: artifactPath,
                                        type: pom.packaging],
                                        // Lets upload the pom.xml file for additional information for Transitive dependencies
                                        [artifactId: pom.artifactId,
                                        classifier: '',
                                        file: "pom.xml",
                                        type: "pom"]
                                        ]
                                    );
                            } else {
                                error "*** File: ${artifactPath}, could not be found";
                            }
                        }
                    }
                }
            }
        }        

        stage('Deployment into Testenvironment'){
            steps {
                echo 'Deploying SCS-Artefakt into testenvironment'
            }
        }

        stage('Smoke Test Testenvironment'){
            steps{
                echo 'Smoke-test'
            }
        }

        stage("Dependency Check") {
            steps{
                sh'mvn org.owasp:dependency-check-maven:check'
                dependencyCheckPublisher(pattern: 'target/dependency-check-report.xml')
            }
        }

        stage('Deployment into E2E'){
            steps {
                echo 'Deploying SCS-Artefakt into E2E'
            }
        }

        stage('Smoke Test E2E'){
            steps{
                echo 'Smoke-test'
            }
        }

        stage('Performance Test'){
            steps{
                echo 'Performance-test'
            }
        }

        stage('Deployment into Prod'){
            steps {
                echo 'Deploying SCS-Artefakt into Prod'
            }
        }

        stage('Smoke Test Prod'){
            steps{
                echo 'Smoke-test'
            }
        }
    }
}