pipeline {
    agent any

    environment {
        SONAR_SCANNER_HOME = tool name: 'Sonarqubescanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
        MAVEN_HOME = tool name: 'MavenLatest', type: 'maven'
    }

    stages {
        stage('Code Checkout') {
            steps {
                echo 'Checking out code'
                git changelog: false, credentialsId: 'GitHubCreds', poll: false, url: 'https://github.com/SiyaaJhawar/SonarQubeCoverageJava'
            }
        }

        stage('Maven Build') {
            steps {
                echo 'Running Maven Build'
                script {
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
                echo 'Running SonarQube Analysis'
                script {
                    withSonarQubeEnv(credentialsId: 'sonarqubetoken') {
                        sh "${SONAR_SCANNER_HOME}/bin/sonar-scanner"
                    }
                }
            }
        }
    }
}
