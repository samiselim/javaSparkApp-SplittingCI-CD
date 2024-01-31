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
        IMAGE_NAME="samiselim/SparkJavaApp"
    }
    stage("build Java App") {
        steps {
            script { 
                echo "building the application for ${BRANCH_NAME}"
                sh 'mvn clean package' // to build just one jar file and deleting any jar files before building
            }
        }
    }
}