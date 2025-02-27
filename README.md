# Project Management Application

## Description
This project is designed for managing tasks and projects within a team. It allows adding employees, assigning tasks, tracking their completion, and more.

## Technologies
- Spring Boot
- Hibernate
- PostgreSQL
- Liquibase
- Apache Kafka
- Docker
- Testcontainers
- JUnit
- SonarQube

## Installation and Running


### Local Setup
1. Ensure you have **Java 21**, **PostgreSQL** and **Docker** installed.

2. Set email and password in **mail/src/main/resources/application.properties**:
   ```properties
   spring.mail.username=your_mail
   spring.mail.password=your_password
   ```
3. Set secret and expire time for your jwt token in **src/main/resources/application.properties**
   ```properties
   jwt.token.secret=your_secret
   jwt.token.expireTime=your_expire_time
   ```

4. Set the required environment variables:
   
   ```DATABASE_URL``` - database connection url

   ```USERNAME``` - database username

   ```PASSWORD``` - database password


   Swagger UI - http://localhost:8080/swagger-ui.html
   
   SonarQube - http://localhost:9000

## Authentication


To use the application, you need to sign in as an admin:
1. Send a POST request to `localhost:8080/auth/signin` with the following JSON body:
   ```json
   {
     "username": "admin",
     "password": "pass"
   }
   ```
2. The response will contain a **Bearer token** that must be used in all subsequent requests by adding it to the `Authorization` header:
   ```
   "accessToken": <your_token>
   ```

## API


### Example Requests
**Create a new project:**
   ```http
   POST /api/projects
   Content-Type: application/json
   Authorization: Bearer <your_token>
   {
     "projectName": "New Project",
     "projectDescription": "Project description"
   }
   ```

**Retrieve all tasks:**
   ```http
   GET /api/tasks
   Authorization: Bearer <your_token>
   ```

Full list of endpoints is available at **Swagger UI**.

## Testing


To run tests, use the following command:
   ```sh
  mvn test
   ```
Tests use **Testcontainers**, **EmbeddedKafka** and **JUnit**.

## Contact


Project author: [vnrgh](https://github.com/vnrgh)

