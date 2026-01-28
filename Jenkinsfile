pipeline {
	agent any
	
	environment {
		DOCKER_USER = 'ouxthm'
		DOCKER_IMAGE = "${DOCKER_USER}/boot-app:latest"
		CONTAINER_NAME = 'boot-app'
		COMPOSE_FILE = 'docker-compose.yml'
		
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
		
			stage('Docker Hub Login') {
				steps {
					echo 'DockerHub Login'
					withCredentials([usernamePassword(
						credentialsId: 'dockerhub-credential',
						usernameVariable: 'DOCKER_ID',
						passwordVariable: 'DOCKER_PW'
					)]){
						sh """
						    echo $DOCKER_PW | docker login -u $DOCKER_ID --password-stdin					
						   """
					}
				}
			}
		
		stage('DockerHub Push') {
			steps {
				echo 'Docker Hub Push'
				sh '''
					docker push ${DOCKER_IMAGE}
				   '''
			}
		}
		
		stage('Docker Compose') {
			steps {
				echo 'docker-compose down'
				sh """
					docker-compose -f ${COMPOSE_FILE} down || true
				   """
			}
		}
		
		stage('Docker Stop And RM') {
			steps {
				echo 'docker stop rm'
				sh """
					docker stop ${CONTAINER_NAME} || true
					docker rm ${CONTAINER_NAME} || true
					docker pull ${DOCKER_IMAGE}
				   """
			}
		}
		
		stage('Docker Compose Up') {
			steps {
				echo 'docker-compose up'
				sh """
					docker-compose -f ${COMPOSE_FILE} up -d
				   """
			}
		}
		/*stage('Docker Run') {
			steps {
				echo 'Docker Run'
				sh '''
					docker stop ${CONTAINER_NAME} || true
					docker rm ${CONTAINER_NAME} || true
					
					docker pull ${DOCKER_IMAGE}
					
					docker run --name ${CONTAINER_NAME} \
					-it -d -p 8080:9090\
					${DOCKER_IMAGE}
				   '''
			}
		}*/
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