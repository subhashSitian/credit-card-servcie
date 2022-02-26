FROM openjdk:17
EXPOSE 8080
ADD target/credit-card-service-0.0.1.jar credit-card-service-0.0.1.jar
ENTRYPOINT ["java","-jar","credit-card-service-0.0.1.jar"]
