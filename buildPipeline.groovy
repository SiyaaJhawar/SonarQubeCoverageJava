node(){
    def sonarScanner = tool name: 'Sonarqubescanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    def MAVEN_HOME = tool name: 'MavenLatest', type: 'maven'
	stage('Code Checkout'){
		git changelog: false, credentialsId: 'GitHubCreds', poll: false, url: 'https://github.com/SiyaaJhawar/MavenBuild'
	}
	stage('Maven Build'){
		sh """
			ls -lart
			date
		 ${MAVEN_HOME}/bin/mvn clean install
			
		"""
	}
	stage('SonarQube Analysis'){
	withSonarQubeEnv(credentialsId: 'sonarqubetoken') {
    sh "ls -lart ${sonarScanner}/bin/sonar-scanner"
}
	    
	}
	stage('Code Deployment'){
	
	}
}


