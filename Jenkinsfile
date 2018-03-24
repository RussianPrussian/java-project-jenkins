pipeline {
	agent none
	options {
		buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
	}

	stages {
		stage('Unit Tests') {
			agent {
				label 'master'
			}
			steps {
				sh 'ant -f test.xml -v'
				junit 'reports/result.xml'
				sh 'cat reports/result.xml'
			}
			post {
				always {
					archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
				}
			}
		}
		stage('build') {
			agent {
				label 'master'
			}
			steps {
				sh 'ant -f build.xml -v'
			}
		}
		stage('deploy') {
			agent {
				label 'master'
			}
			steps {
				sh "cp dist/rectangle_${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/"
			}
		}
		stage('Running on Centos') {
			agent {
				label 'master'
			}
			steps {
			  sh "wget http://russianprussian1.mylabserver.com/rectangles/all/rectangle_${env.BUILD_NUMBER}.jar"
			  sh "java -jar rectangle_${env.BUILD_NUMBER}.jar 3 4"
			}
		}
	}

	
}
