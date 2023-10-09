node {
   def mvn = tool name: 'MavenLatest', type: 'maven'
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
   
    	withSonarQubeEnv(credentialsId: 'sonarqubetoken') {
      // Replace 'SonarQubeServer' with the ID of your configured SonarQube server
      sh "${mvn}/bin/mvn clean install sonar:sonar -Dsonar.projectKey=SonarQubeScanner"
    }
  }
}
