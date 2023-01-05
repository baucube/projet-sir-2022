pipeline{
    agent any
    tools{
        maven '3.8.6'
    }

    stages{
        stage('Source') {
            steps{
                git branch: 'main', url: 'https://github.com/baucube/projet-sir-2022.git'
            }
        }

        stage ('Build') {
            steps{
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
            }
        }
        stage ('SonarQube Analysis') {
            steps{
                sh 'mvn sonar:sonar'
            }
        }

        stage ('Approve Deployment') {
            input {
                message 'Do you want to proceed for deployment?'
            }
            steps{
                sh 'echo "Deploying into Server dev."'
            }
        }
    } // stages

    post {
        aborted {
            echo "Sending message to Agent"
        } // aborted

        failure {
            echo "Sending message to Agent"
        } // failure

        success {
            echo "Sending message to Agent"
        } // success
    } // post

}