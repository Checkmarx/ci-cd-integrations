pipeline {
    agent any 
    stages {
        stage('Scan') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/WebGoat/WebGoat']]])

                checkmarxASTScanner useOwnAdditionalOptions: true, 
                    additionalOptions: '--debug --sast-filter "!src/"', 
                    serverUrl: 'https://ast.checkmarx.net/', 
                    baseAuthUrl: '', 
                    tenantName: 'mytenant', 
                    branchName: 'main', 
                    checkmarxInstallation: 'local', 
                    credentialsId: 'mycredentials', 
                    projectName: 'webgoat',
                    useOwnServerCredentials: true
        
            }
        }
    }
    post { 
        always { 
            cleanWs()
        }
    }
}