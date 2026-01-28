   pipeline {
      agent any
      
      environment {
         DOCKER_IMAGE = "ouxthm/awscicd-app"
         DOCKER_TAG = "latest"
         EC2_HOST = "13.125.173.64"
         EC2_USER = "ubuntu"
      }
      stages{
         stage('Checkout'){
            steps{
               echo 'Git Checkout'
               checkout scm
            }
         }
         
         stage('Gradlew Build') {
            steps{
               echo 'Gradle Build'
               sh """
                   chmod +x gradlew
                   ./gradlew clean build -x test
                  """
            }
         }
         
         stage('Docker build'){
            steps {
               echo 'Docker Image build'
               sh """
                   docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                   
                   """
               
            }
         }
         
         stage('Docker Hub Login'){
            steps {
               echo 'Docker Hub Login'
               withCredentials([usernamePassword(
                  credentialsId: 'dockerhub-credential',
                  usernameVariable: 'DOCKER_ID',
                  passwordVariable: 'DOCKER_PW'
               )]){
                  sh """
                      echo $DOCKER_PW | docker login -u $DOCKER_ID --password-stdin
                      docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                     """
               }
               
            }
            
            
         }
         
         stage('Deploy to EC2'){
            steps{
               sshagent(['ec2-ssh-key']){
               sh """
                  ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} << EOF
                       docker stop awscicd || true
                       docker rm awscicd || true
                       docker pull ${DOCKER_IMAGE}:${DOCKER_TAG}
                       docker run --name awscicd -it -d -p 8080:9090 ${DOCKER_IMAGE}:${DOCKER_TAG}
                  """
                
               }
            }
            
         }
         
         /*
         stage('Docker Compose Down'){
            steps{
               echo 'Docker-compose down'
               sh """
                  docker-compose -f ${COMPOSE_FILE} down || true
                  """
            }
         }
         */
         /*
         stage('Docker Stop And RM'){
            steps{
               echo 'docker stop rm'
               sh """
                  docker stop ${CONTAINER_NAME} || true
                  docker rm ${CONTAINER_NAME} || true
                  docker pull ${IMAGE_NAME}
                  """
            }
            
         }   
         stage('Docker Compose up'){
            steps{
               echo 'Docker-compose up'
               sh """
                  docker-compose -f ${COMPOSE_FILE} up -d
                 """
            }
            
         }
         */
         /*stage('Docker Run'){
            steps{
               echo 'Docker Run'
               sh """
                  docker stop ${CONTAINER_NAME} || true
                  docker rm ${CONTAINER_NAME} || true
                  
                  docker pull ${IMAGE_NAME}
                  
                  docker run --name ${CONTAINER_NAME} \
                  -it -d -p 9090:9090 \
                  ${IMAGE_NAME}
                  """
               
            }
         }*/
         
          
      
      }
      post {
            success{
               echo 'CI/CD 실행 성공'
            }
            failure {
               echo 'CI/CD 실행 실패'
               
            }
         }
   }