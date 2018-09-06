# myRetailApi Case Study

This project is a proof of concept API using redSky.target.com and mongoDB to return title and pricing info of retail products.

### Installation/Setup

You will need:

Java, Maven, Git, Spring Boot

A REST client for pinging the API.

An installed version of MongoDB.

Note: Jetbrains' IntelliJ IDE is recommended for easy setup and use, but feel free to choose your own.

Clone the Repo with:
 
```
git clone https://github.com/EthanCoyne/myRetailApi.git

```

### Creating DB and adding test data:
- In your IDE, choose a name for your pricing database and create an environment variable DB_NAME and set the value to the name you have chosen.(e.g. PricingDb)
- in ROOT/src/main/resources/application.properties, add the line: 
```
spring.data.mongodb.uri = mongodb://localhost:27017/${DB_NAME}
```

 - Serve an instance of MongoDB on a local port (default is 27017).
 - Using your terminal or a GUI such as Robo3T, create a database with the same name you chose for the environment variable above.
 - Create a collection called ```current_pricing```, or choose your own and change the @Document annotation at the top of CurrentPricing.java to match.
 - add a few current_pricing documents to use as test data with the following format:
 ```
 {productId: 13860428, value: 14.99, currency_code: USD}

```
- Other known working productIds are:
```
13860429, 13860432, 13860432
```

- For more help with setting up and serving an instance of MongoDB, refer to their [documentation](https://docs.mongodb.com/manual/) 
 
### Usage
- In your project IDE, run CaseStudyApp to spin up server on your local machine

#### Retrieving a product (GET)
- Using any REST client, make a GET request with the desired productId as the parameter:
```
http://localhost:8080/{productId} 
```

example output: 
```
{"id":13860428,"name":"The Big Lebowski (Blu-ray)","current_price":{"value":259.99,"currency_code":"USD"}}
```
 
#### Updating product pricing & currency code (PUT)
- Using any REST client and the same URL, make a PUT request with a request body with the format:
```
{
  "productId": 13860428,
  "value": 11.99,
  "currency_code": "USD" 
} 
``` 
 

## Running Unit Tests

- With Maven installed, you can open your terminal in your project and run this command to execute all unit tests:
```$xslt
mvn clean test

```
- This will generate report files in the ```ROOT/target/surefire-reports``` directory
                                      
- Alternatively, you can run the tests using your IDE.

## Built With

* [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Maven](https://maven.apache.org/) - Dependency Management
* [MongoDB](https://docs.mongodb.com/manual/) - NoSQL data store 



## Author

* **Ethan Coyne** - 
- email: ethancoyne01@gmail.com
