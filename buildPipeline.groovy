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
	stage('SonarQube Analysis') {
    def mvn = tool name: 'MavenLatest', type: 'maven'
  withSonarQubeEnv(credentialsId: 'sonarqubetoken') {
			sh "${sonarScanner}/bin/sonar-scanner"
      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=SonarQubeScanner"
}
	    
	}
	stage('Code Deployment'){
	
	}
}
