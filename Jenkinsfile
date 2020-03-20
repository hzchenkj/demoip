pipeline {
    agent any

    environment{
        CC = 'clang'
    }

    tools {
        jdk 'jdk-8u162'
        maven 'mvn-3.6.1'
    }

    stages {
       
        stage('Example'){
            environment{
                DEBUG_FLAGS = '-g'  
            }

            steps {
                sh "${CC} ${DEBUG_FLAGS}"  
            }
        }


        stage('java 8') {
            steps {
                echo 'Hello World'
                echo "Running ${env.BUILD_NUMBER} on ${env.JENKINS_URL} git: ${env.GIT_BRANCH}"
                sh 'java -version'
                sh 'javac -version'
            }
        }

        stage('Build'){
            steps {
                sh 'mvn clean package -DskipTests'
                sh 'printenv'
            }
        }
    }

}
