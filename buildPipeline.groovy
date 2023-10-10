
node {
    def sonarScanner = tool name: 'Sonarqubescanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    
    stage('Code Checkout') {
        checkout scm
        git credentialsId: 'GitHubCreds', poll: false, url: 'https://github.com/SiyaaJhawar/MavenBuild'
    }

    stage('Maven Build') {
        sh """
            ls -lart
            date
            ${mvn}/bin/mvn clean install
        """
    }

 
  stage('SonarQube Analysis') {
  def mvn= tool name: 'MavenLatest', type: 'maven'

    withSonarQubeEnv() {
      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=SonarQubeScanner"
    }
  }



    stage('Code Deployment') {
        // Add deployment steps here
    }
}
