credit-card-service
==
## Description

This service exposes 2 secure endpoint

* Add card details into database
* Retrieve all stored card details from table

Adding new card service validate make sure to not store duplicate card which is already into database and before storing the card details it validates the card number using LUHN algorithm.

Retrieve card details service just goes and fetches all data from table if any 

Both the services has custom validation and exception handling and make use of basic sprint security authorization. 

service uses H2 database to hold and retrieve card details.

## Getting Started

### Dependencies

* Java 8+
* Springboot 2.6.3
* Maven 3

### Steps to run

* Build the project using mvn clean install
* Run using mvn spring-boot:run
* The web application is accessible via localhost:8080
* Use username and password as 'admin' to access service endpoints

```
mvn clean install
mvn spring-boot:run
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
