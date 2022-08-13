pipeline {
    agent any
    stages {
        stage('check maven version') {
            steps {
                echo "I am making build of Click Pay."
                git url: "https://github.com/qaximalee/clickpay.git",
                credentialsId: 'qaximalee'
                bat "mvnw clean"
                bat "mvnw install"
                bat "java -jar target/clickpay-0.0.1-SNAPSHOT.jar"
            }
        }
    }
}