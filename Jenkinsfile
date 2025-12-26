pipeline {
    agent any
    tools {
        maven 'Maven 3' // Adjust to your Jenkins Maven tool name
        jdk 'JDK 11'    // Adjust to your Jenkins JDK tool name
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build & Test') {
            steps {
                dir('selenium-tests') {
                    sh 'mvn clean verify -Pdev'
                }
            }
            post {
                always {
                    junit 'selenium-tests/target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'selenium-tests/target/allure-results/**', allowEmptyArchive: true
                }
            }
        }
        stage('Allure Report') {
            steps {
                allure includeProperties: false, jdk: '', results: [[path: 'selenium-tests/target/allure-results']]
            }
        }
    }
}
