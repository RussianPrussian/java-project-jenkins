pipeline {
	agent {
		label 'Slave1'
	}
	stages {
	  stage('build'){
	  	steps{
	   	 	sh 'ant -f build.xml -v'
	   	 }
	  }
	}
}
