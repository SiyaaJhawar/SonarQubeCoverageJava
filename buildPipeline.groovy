pipeline {
    agent any

    environment {
        SONAR_SCANNER_HOME = tool name: 'Sonarqubescanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
        MAVEN_HOME = tool name: 'MavenLatest', type: 'maven'
    }

    stages {
        stage('Code Checkout') {
            steps {
                git changelog: false, credentialsId: 'GitHubCreds', poll: false, url: 'https://github.com/SiyaaJhawar/SonarQubeCoverageJava'
            }
        }

        stage('Maven Build') {
            steps {
                script {
                    echo 'Running Maven Build'
                    sh """
                        ls -lart
                        date
                        ${MAVEN_HOME}/bin/mvn clean install
                    """
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    echo 'Running SonarQube Analysis'
                    withSonarQubeEnv(credentialsId: 'sonarqubetoken') {
                        sh "${SONAR_SCANNER_HOME}/bin/sonar-scanner"
                    }
                }
            }
        }

        stage('Code Deployment') {
            steps {
                // Add deployment steps as needed
            }
        }
    }
}
