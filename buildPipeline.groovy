
node {
    def sonarScanner = tool name: 'Sonarqubescanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    def mvnw = tool name: 'MavenLatest', type: 'maven'

    stage('Code Checkout') {
        checkout scm
        git credentialsId: 'GitHubCreds', poll: false, url: 'https://github.com/SiyaaJhawar/MavenBuild'
    }

    stage('Maven Build') {
        sh """
            ls -lart
            date
            ${mvnw}/bin/mvn clean install
        """
    }

  stage('SonarQube Analysis') {
    withSonarQubeEnv(installationName: 'Sonarqubeserver') {
        sh "./mvnw clean org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594:sonar"
    }
}


    stage('Code Deployment') {
        // Add deployment steps here
    }
}
