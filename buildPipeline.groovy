
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
     sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=jenkins"
    }
  }
}

    }
  }



    stage('Code Deployment') {
        // Add deployment steps here
    }
}
