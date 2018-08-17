pipeline {
  agent any
  environment {
      def USERMAIL = "big.meng@qq.com"
  }
  stages {
    stage('Copy the necessary files') {
      steps {
        script{
            try{
                sh 'cp ~/qiniu.properties admin/src/main/resources/qiniu.properties'
            }catch (exc) {
                 currentBuild.result = "FAILURE"
                 emailext (
                    subject: "'${env.JOB_NAME} [${env.BUILD_NUMBER}]' 更新失败",
                    body: """
                    详情：
                    FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'
                    状态：${env.JOB_NAME} jenkins 更新失败
                    URL ：${env.BUILD_URL}
                    项目名称 ：${env.JOB_NAME}
                    项目更新进度：${env.BUILD_NUMBER}
                    内容：qiniu.peoperties 不存在
                    """,
                    to: "${USERMAIL}",
                    recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                 )
            }
        }
      }
    }
    stage('build') {
      steps {
        script{
            try {
                sh './gradlew build'
            } catch (exc) {
                currentBuild.result = "FAILURE"
                emailext (
                   subject: "'${env.JOB_NAME} [${env.BUILD_NUMBER}]' 更新失败",
                   body: """
                   详情：
                   FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'
                   状态：${env.JOB_NAME} jenkins 更新失败
                   URL ：${env.BUILD_URL}
                   项目名称 ：${env.JOB_NAME}
                   项目更新进度：${env.BUILD_NUMBER}
                   内容：构建失败
                   """,
                   to: "${USERMAIL}",
                   recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                )
            }
        }
      }
    }
    stage('publish') {
      steps {
        sh '''\\cp -f admin/build/libs/admin-*.jar ~/cms/
echo \'copy\' `ls admin/build/libs/admin-*.jar | tr \'\\n\' \'\\0\' | xargs -0 -n 1 basename` \'to ~/cms/!\''''
      }
    }
    stage('send mail') {
      steps {
        emailext (
           subject: "'${env.JOB_NAME} [${env.BUILD_NUMBER}]' 更新正常",
           body: """
           详情：
           SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'
           状态：${env.JOB_NAME} jenkins 更新运行正常
           URL ：${env.BUILD_URL}
           项目名称 ：${env.JOB_NAME}
           项目更新进度：${env.BUILD_NUMBER}
           """,
           to: "${USERMAIL}",
           recipientProviders: [[$class: 'DevelopersRecipientProvider']]
        )
      }
    }
  }
}