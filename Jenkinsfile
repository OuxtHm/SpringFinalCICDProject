pipeline {
	agent any
	
	environment {
		DOCKER_IMAGE = 'ouxthm/boot-app:latest'
		CONTAINER_NAME = 'boot-app'
	}
	stages{
		stage('Checkout'){
			steps{
				echo 'Git Checkout'
				checkout scm
			}
		}
		
		stage('Gradlew Build') {
			steps {
				echo 'Gradle Build'
				sh '''
					chmod +x gradlew
					./gradlew clean build -x test
				   '''	
			}
		}
		
		stage('Docker Build') {
			steps {
				echo 'Docker Image Build'
				sh '''
					docker build -t ${DOCKER_IMAGE} .
				   '''
			}
		}
		
		stage('Docker Run') {
			steps {
				echo 'Docker Run'
				sh '''
					docker stop ${CONTAINER_NAME} || true
					docker rm ${CONTAINER_NAME} -d -p 8081:9090 ${DOCKER_IMAGE}
					
					docker run --name ${CONTAINER_NAME} \
					-it -d -p 9090:9090\
					${DOCKER_IMAGE}
				   '''
			}
		}
	}
	
	post {
		success {
			echo 'Docker 실행 성공'
		}
		failure {
			echo 'Docker 실행 실패'
		}
	}
}