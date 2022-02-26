// Build docker image
cd /credit-card-service
docker build -t credit-card-service .

// run it on local machine
docker run -p 9090:8080 credit-card-service

// Push to Docker Hub
docker tag credit-card-service sitian/credit-card-service

//Login to DockerHub
docker login ( Provide user id and password)

//Pull docker Image from Docker Hub and run it on machine
* docker pull sitian/credit-card-service:latest
* docker run -p 9090:8080 sitian/credit-card-service