#!/usr/bin/env groovy
// @Library('jenkins-shared-lib')

// without adding this lib globally in jenkins 
library identifier: 'jenkins-shared-lib@main',retriever: modernSCM(
    [$class: 'GitSCMSource',
      remote: 'https://github.com/samiselim/jenkins-shared-lib.git',
      credentialsId: 'sami_githubAcess'
    ]
)

pipeline {
    agent any
    tools{
        maven 'Maven'
    }
    environment{
        IMAGE_NAME="samiselim/spark-java-app"

    }
    stages{
        stage("Increment Version"){
            steps{
                script{
                    echo "Incrementing App Version"
                    
                    sh 'mvn build-helper:parse-version versions:set \
                    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion}\
                    versions:commit'
                    
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'  // this code is reguler expression to extract version tag fro xml file 
                    def version = matcher[0][1] // to extract the text of version only 
                    env.IMAGE_VERSION = "$version"
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
                    withCredentials([usernamePassword(credentialsId: 'sami_docker_hub_credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        script.sh "echo $PASS | docker login -u $USER --password-stdin"
                    }
                        
                    dockerBuildImage("${env.IMAGE_NAME}")
                    dockerPush("${env.IMAGE_NAME}")
                }
            }
                  
        }
    }
}