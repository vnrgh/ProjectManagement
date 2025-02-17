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

## Installation and Running
### Local Setup
1. Ensure you have **Java 21** and **Docker** installed.
2. Clone the repository:
   ```sh
   git clone https://github.com/vnrgh/ProjectManagement
   cd ProjectManagement
   ```
3. Set the required environment variables:
   ```sh
   export DATABASE_URL=<your_database_url>
   export USERNAME=<your_db_username>
   export PASSWORD=<your_db_password>
   export SECRET=<your_secret_key>
   export EXPIRE_TIME=<token_expiration_time>
   ```
4. Start database containers:
   ```sh
   docker-compose up -d
   ```
5. Build and run the project:
   ```sh
   ./mvnw spring-boot:run
   ```

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

## Testing
To run tests, use the following command:
```sh
./mvnw test
```
Tests use **Testcontainers** for PostgreSQL.

## Contact
Project author: [vnrgh](https://github.com/vnrgh)

