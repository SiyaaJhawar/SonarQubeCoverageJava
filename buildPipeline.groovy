node(){
    def sonarScanner = tool name: 'Sonarqubescanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
	stage('Code Checkout'){
		git changelog: false, credentialsId: 'GitHubCreds', poll: false, url: 'https://github.com/SiyaaJhawar/MavenBuild'
	}
	stage('Maven Build'){
		sh """
			ls -lart
			date
			mvn clean install
			
		"""
	}
	stage('code review'){
	withSonarQubeEnv(credentialsId: 'sonarqubetoken') {
    sh "ls -lart ${sonarScanner}/bin/sonar-scanner"
}
	    
	}
	stage('Code Deployment'){
	
	}
}
