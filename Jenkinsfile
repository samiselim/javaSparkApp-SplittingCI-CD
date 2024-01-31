#!/usr/bin/env groovy
// @Library('jenkins-shared-lib')

// without adding this lib globally in jenkins 
library identifier: 'jenkins-shared-lib@main',retriever: modernSCM(
    [$class: 'GitSCMSource',
      remote: 'https://github.com/samiselim/jenkins-shared-lib.git',
      credentialsId: 'sami_githubAcess'
    ]
)
def gv
pipeline {
    agent any
    tools{
        maven 'Maven'
    }
    environment{
        IMAGE_NAME="samiselim/spark-java-app"

    }
    stages{
        stage(" loading groovy scripts") {
            steps {
                script { 
                    gv = load "script.groovy"
                }
            }
        }
        stage("Increment Version"){
            steps{
                script{
                    echo "****************** Starting Incrementing Version  **************"
                    gv.incVersion()
                }
            }
        }
        stage("build Java App") {
            steps {
                script { 
                    echo "building the application for ${BRANCH_NAME}"
                    sh 'mvn clean package' // to build just one jar file and deleting any jar files before building
                }
            }
        
        }
        stage("build image") {
            steps {
                script {
                    echo "Building Docker Image and Pusshing it with new version to Dockerhub Repo"
                    dockerLogin('sami_docker_hub_credentials')
                    dockerBuildImage("${env.IMAGE_NAME}")
                    dockerPush("${env.IMAGE_NAME}")
                }
            }
        }
    }
}