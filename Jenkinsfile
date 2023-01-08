pipeline{
    agent any
    tools{
        maven '3.8.6'
    }

    stages{
        stage('Source SonarQube') {
            steps{
                git branch: 'main', url: 'https://github.com/baucube/projet-sir-2022.git'
            }
        }

        stage ('Build') {
            steps{
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
            }
        }
        stage ('SonarQube') {
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
        stage('Build Docker image yes'){
            steps{
                script{
                    sh 'docker build -t baucube/groupe3:1  .'
                }
            }
        }

        stage('Push Image to Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhubid', variable: 'dockerhubpassword')]) {
                        // some block
                        sh 'docker login -u baucube -p ${dockerhubpassword}'

                        sh 'docker push baucube/groupe3:1'
                    }
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