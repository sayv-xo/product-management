pipeline {
    agent any

    environment {
        TEST_PORT = '8080'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install Dependencies') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Start MySQL Service') {
                    steps {
                        script {
                            def serviceStatus = bat(script: 'sc query MySQL84', returnStatus: true)
                            if (serviceStatus == 0) {
                                echo "MySQL84 service is already running. Skipping start."
                            } else {
                                bat 'net start MySQL84'
                            }
                        }
                    }
        }

        stage('Start Backend Service') {
            steps {
                bat '''
                    start /B java -jar target/product-management-0.0.1-SNAPSHOT.war
                '''
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test -Dtest.port=${TEST_PORT}'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            script {
                def jobName = env.JOB_NAME
                def buildNumber = env.BUILD_NUMBER
                def pipelineStatus = currentBuild.result ?: 'UNKNOWN'
                def bannerColor = pipelineStatus.toUpperCase() == 'SUCCESS' ? 'green' : 'red'
                def body = """
                <html>
                <body>
                <div style="border: 4px solid ${bannerColor}; padding: 10px;">
                <h2>${jobName} - Build ${buildNumber}</h2>
                <div style="background-color: ${bannerColor}; padding: 10px;">
                <h3 style="color: white;">Pipeline Status: ${pipelineStatus.toUpperCase()}</h3>
                </div>
                <p>Check the <a href="${env.BUILD_URL}">console output</a>.</p>
                </div>
                </body>
                </html>
                """
                emailext (
                    subject: "${jobName} - Build ${buildNumber} - ${pipelineStatus.toUpperCase()}",
                    body: body,
                    to: 'savegawugah01@gmail.com',
                    from: 'savegawugah01@gmail.com',
                    replyTo: 'jenkins@example.com',
                    mimeType: 'text/html',
                    attachmentsPattern: 'target/surefire-reports/*.xml'
                )
            }
        }
    }
}
