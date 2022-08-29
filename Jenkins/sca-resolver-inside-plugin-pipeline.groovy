pipeline {
    agent any 
    stages {
        stage('Scan') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/WebGoat/WebGoat']]])
                
                withMaven(maven: 'M3') {
                    sh 'curl -L -o ScaResolver-linux64.tar.gz https://sca-downloads.s3.amazonaws.com/cli/latest/ScaResolver-linux64.tar.gz'
                    sh 'tar -xzvf ScaResolver-linux64.tar.gz -C /tmp/'
                    sh 'rm -rf ScaResolver-linux64.tar.gz'
                    
                    checkmarxASTScanner useOwnAdditionalOptions: true, 
                        additionalOptions: '--debug --sca-resolver /tmp/ScaResolver --sca-resolver-params "--log-level Debug --ignore-dev-dependencies true"', 
                        serverUrl: 'https://ast.checkmarx.net/', 
                        baseAuthUrl: '', 
                        tenantName: 'mytenant', 
                        branchName: 'main', 
                        checkmarxInstallation: 'local', 
                        credentialsId: 'mycredentials', 
                        projectName: 'webgoat',
                        useOwnServerCredentials: true
                    
                    sh 'rm /tmp/ScaResolver'
                }
            }
        }
    }
    post { 
        always { 
            cleanWs()
        }
    }
}