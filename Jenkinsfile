pipeline{
    agent any
    tools{
        maven '3.8.6'
    }

    environment {
            registry = "baucube/sir2022:1"
            registryCredential = 'dockerhub'
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
        stage ('SonarQube Analysis must work.') {
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

         stage('Building image') {
              steps{
                    script {
                         docker.build registry + ":$BUILD_NUMBER"
                    }
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