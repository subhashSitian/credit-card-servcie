credit-card-service
==
## Description

This service exposes 2 secure endpoint

* Add card details into database
* Retrieve all stored card details from table

Adding new card detail service validates input request and make sure to not store duplicate card details which is already into database. Before storing any card deatils, it validates the card number using LUHN algorithm.

Retrieve card details service just goes and fetches all data from table if any 

Both the services has custom validation and exception handling and make use of basic spring security authorization. 

Service uses H2 database to hold and retrieve card details.


## Getting Started

### Dependencies

* Java 8+ / Compiled using java 17
* Springboot 2.6.3
* Maven 3
* JPA/H2
* Mockito,Junit
* Spring Basic authentication
* Lombok
* java validation
* DOCKER

### Steps to run : Maven and Container(Docker Image)

* Build the project using mvn clean install
* Run using mvn spring-boot:run
* The web application is accessible via localhost:8080
* Use username and password as 'admin' to access service endpoints

```
Maven

mvn clean install
mvn spring-boot:run
```


```
Docker Image

#Pull image from docker hub
 docker pull sitian/credit-card-service:latest

# Run Docker Image to Access Service
  docker run -p 8080:8080 sitian/credit-card-service

```

### Add Card : POST
```
http://localhost:8080/api/v1/credit-card/add

Valid Request:
{
    "customerName":"subhash sharma",
    "cardNumber": 49927398716,
    "balanceAmount": -234.12
}

Invalid Request:

{
    "customerName":"subhash sharma",
    "cardNumber": 1234567812345678, // It fail with LUHN algo
    "balanceAmount": -234.12
}
```


### Retrieve Card :GET 
```
http://localhost:8080/api/v1/credit-card/retrieve-all

Response:
{
    "customerName":"subhash sharma",
    "cardNumber": 49927398716,
    "balanceAmount": -234.12
}
```

## Help

H2 databse is accessable through below link
```
http://localhost:8080/h2-console/login.jsp
```

## Authors

Subhash Sharma


## Version History

* 0.1
    * Initial Release
