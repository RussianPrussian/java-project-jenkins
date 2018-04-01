pipeline {
	agent none

	options {
		buildDiscarder(logRotator(numToKeepStr: '2', artifactNumToKeepStr: '1'))
	}

	environment {
		MAJOR_VERSION = 1
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

		}
		stage('build') {
			agent {
				label 'master'
			}
			steps {
				sh 'ant -f build.xml -v'
			}

			post {
				success {
					archiveArtifacts artifacts: 'dist/*.jar', fingerprint: true
				}
			}
		}
		stage('deploy') {
			agent {
				label 'master'
			}
			steps {
				sh "mkdir -p /var/www/html/rectangles/all/${env.BRANCH_NAME}"
				sh "cp dist/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/all/${env.BRANCH_NAME}"
			}
		}
		stage('Running on Centos') {
			agent {
				label 'master'
			}
			steps {
			  sh "wget http://russianprussian1.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
			  sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 3 4"
			}
		}
		stage("Test on Debian") {
			agent  {
				docker 'openjdk:8u162'
			}
			steps {
			  sh "wget http://russianprussian1.mylabserver.com/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar"
			  sh "java -jar rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar 3 4"
			}
		}
		stage("Promote to Green") {
			agent {
				label 'master'
			}
			when {
				branch 'master'
			}
			steps {
				sh "cp /var/www/html/rectangles/all/${env.BRANCH_NAME}/rectangle_${env.MAJOR_VERSION}.${env.BUILD_NUMBER}.jar /var/www/html/rectangles/green/"
			}
		}
		stage('Promote Development branch to Master') {
			agent {
				label 'master'
			}
			when {
				branch 'development'
			}
			steps {
				echo "Stashing any local changes"
				sh "git stash"
				echo "Checking out development"
				sh "git checkout development"
				echo "Doing git pull"
				sh "git pull origin development"
				echo "Checking out master branch"
				sh "git checkout master"
				echo "pulling remote master"
				sh "git pull origin master"
				echo "merging development into master branch"
				sh "git merge development"
				echo "Pushing to Origin master"
				sh "git push origin master"
				echo "Tagging the release"
				sh "git tag rectangle-${env.MAJOR_VERSION}.${env.BUILD_NUMBER}"
			}
			post{
				success {
					emailext(
						subject: "${env.JOB_NAME} [${env.BUILD_NUMBER}] succeeded!",
						body: "<p>${env.JOB_NAME} [${env.BUILD_NUMBER}] succeeded. Go
						to <a href=&QUOT;${env.BUILD_URL}&QUOT;>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a> for more details</p>",
						to: "alexander.katsen@gmail.com"
					)
				}
			}
		}
	}
	post{
		failure {
			emailext(
				subject: "${env.JOB_NAME} [${env.BUILD_NUMBER}] failed!",
				body: "<p>${env.JOB_NAME} [${env.BUILD_NUMBER}] failed.
				Go to <a href=&QUOT;${env.BUILD_URL}&QUOT;>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a> for more details</p>",
				to: "alexander.katsen@gmail.com"
			)
		}
	}
}
