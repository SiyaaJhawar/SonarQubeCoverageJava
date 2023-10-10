
node {
    def sonarScanner = tool name: 'Sonarqubescanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    def mvn = tool 'MavenLatest'

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
        withSonarQubeEnv(credentialsId: 'sonarqubetoken')) {
            sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=SonarQubeScanner"
        }
    }

    stage('Code Deployment') {
        // Add deployment steps here
    }
}
