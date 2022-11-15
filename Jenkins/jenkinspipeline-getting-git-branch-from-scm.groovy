pipeline {
    agent any
    stages {
        stage('Scan') {
            steps {
                script {
                    def scmVars = checkout([$class: 'GitSCM', branches: [[name: '*/develop']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/WebGoat/WebGoat']]])
                    checkmarxASTScanner additionalOptions: '--async',
                            baseAuthUrl: '',
                            branchName: scmVars.GIT_BRANCH,
                            checkmarxInstallation: 'latest',
                            credentialsId: 'ce75aa2f-9a93-4fe7-98f2-87f9bdc29d93',
                            projectName: 'ast-cli',
                            serverUrl: 'https://ast-master.dev.cxast.net/',
                            tenantName: 'master_tenant',
                            useOwnAdditionalOptions: true,
                            useOwnServerCredentials: true
                }
            }
        }
    }
}