pipeline {
    agent any

    tools {
        jdk 'jdk-21'
        maven 'maven-3.9'
    }
     environment {
            SONAR_SCANNER_HOME = tool 'sonar-scanner'
        }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/joshiupendra1995/jenkins-demo-project'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

         stage('Build') {
                    steps {
                        sh 'mvn clean package -DskipTests'
                    }
          }

         stage('SonarQube Analysis') {
                    steps {
                        withSonarQubeEnv('SonarQube') {
                            sh """
                              mvn sonar:sonar \
                              -Dsonar.projectKey=jenkins-demo \
                              -Dsonar.projectName=jenkins-demo \
                              -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                            """
                        }
                    }
                }

              stage('Quality Gate') {
                  steps {
                      waitForQualityGate abortPipeline: true
                  }
              }
    }

    post {
        success {
            echo '✅ Test & Build Successful'
        }
        failure {
            echo '❌ Pipeline Failed'
        }
    }
}