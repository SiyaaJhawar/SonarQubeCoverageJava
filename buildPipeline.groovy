node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool name: 'MavenLatest', type: 'maven'
    withSonarQubeEnv(redentialsId: 'Sonarqubetoken') {
      // Replace 'SonarQubeServer' with the ID of your configured SonarQube server
      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=SonarQubeScanner"
    }
  }
}
