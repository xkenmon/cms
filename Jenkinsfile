pipeline {
  agent any
  stages {
    stage('Copy the necessary files') {
      steps {
        sh 'cp /root/qiniu.properties admin/src/main/resources/qiniu.properties'
      }
    }
    stage('build') {
      steps {
        sh './gradlew build'
      }
    }
  }
}