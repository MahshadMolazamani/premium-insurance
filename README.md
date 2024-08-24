# Insurance Management System

### System built with Spring Boot, JPA, and Docker.

## Build

To clean and build the project without running tests, execute:

```shell
mvn clean install -DskipTests
```

## Test

To run with tests, execute:

```shell
mvn clean install
```

## Deployment

To deploy the application using Docker Compose, execute:

```shell
docker compose --file docker-compose.yml --project-name insurance up --build -d
```

## API Documentation

You can access the API documentation at:

```yaml
URL: http://localhost:8080/swagger-ui/index.html
```