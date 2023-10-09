pipeline {
  agent any

  stages {
    stage('SCM') {
      steps {
        checkout scm
      }
    }

    stage('SonarQube Analysis') {
      steps {
        script {
          def mvnHome = tool name: 'MavenLatest', type: 'maven'
          withSonarQubeEnv(credentialsId: 'sonarqubetoken') {
            sh "${mvnHome}/bin/mvn clean install sonar:sonar -X -Dsonar.projectKey=SonarQubeScanner"

          }
        }
      }
    }
  }
}
