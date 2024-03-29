PhoneNumbers V1 API
=====

This repository contains the PhoneNumbers V1 API

Usage
=====

To run the api you need the following requirements
* JDK 21+
* Maven

To run the application run
```shell
mvn spring-boot:run
```

The api will be available at: http://localhost:8080/api/v1/phonenumbers

Live API Docs are available at: http://localhost:8080/api/v1/swagger-ui/index.html

Native Image
==========
To build the application as a native image using Docker run
```shell
mvn -Pnative spring-boot:build-image
```

To run the resulting image 
```shell
docker run docker.io/library/phone-numbers-v1:0.0.1-SNAPSHOT
```

Test Coverage
=====

To run unit and integration tests
```shell
mvn clean test verify
```

Coverage reports will then be generated under the `target/site/` directory and can be
viewed locally by loading up the `target/site/jacoco/index.html` file on a browser

Coverage threshold is configured using jacaco plugin as 80% line coverage

Design Notes
======

*API Design*

The API was designed with a contract first approach using openapi3 spec. The specification file
can be found here [api-docs.yaml](src/main/resources/static/api-docs.yaml). A live version with swagger ui is also 
available when running the application at: http://localhost:8080/api/v1/swagger-ui/index.html

*Datastore*

The API utilizes an in-memory H2 database for persistence and sample data are loaded using flyway migration
