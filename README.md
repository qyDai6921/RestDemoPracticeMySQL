# RestDemoPracticeMySQL

## Main application
Technologies used - Spring Boot, Spring Data JPA, Swagger
Database - MySQL
Start the application by running RestDemoPracticeApplication class
Configuration file - application.properties
data.sql and schema.sql need to be created manually.
Swagger generated API doc can be accessed from localhost:port/swagger-ui.html

## Test
Technologies used - Spring Test, JUnit, REST Assured
Run mvn install or mvn jacoco:report will generate test coverage report in target/site/jacoco/index.html

## Todo
For Simplicity, no security. We may add a basic http authentication later.
Jenkins integration / Email Notification
Added Trigger for Jenkins
