pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: '*/main']],
                          extensions: [],
                          userRemoteConfigs: [[credentialsId: 'b1b0d61d-cae1-4757-89bb-5fa680a80731', url: 'https://github.com/Checkmarx/ast-cli.git']]])
            }
        }
        stage('Checkmarx Scan') {
            steps {
                checkmarxASTScanner additionalOptions: '--async',
                        baseAuthUrl: '',
                        branchName: 'main',
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
