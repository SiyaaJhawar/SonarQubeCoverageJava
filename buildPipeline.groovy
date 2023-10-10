
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
            mvn clean install
        """
    }

 
  stage('SonarQube Analysis') {
  def mvn= tool name: 'MavenLatest', type: 'maven'

    withSonarQubeEnv('Sonarqubeserver') {
     mvn clean verify sonar:sonar \
  -Dsonar.projectKey=SonarQubeScanner \
  -Dsonar.host.url=http://13.232.1.50:9000 \
  -Dsonar.login=sqp_60056ebaf147e2b0f7c852dd47db09fbb1990ef4 \
    }
  }



    stage('Code Deployment') {
        // Add deployment steps here
    }
}
