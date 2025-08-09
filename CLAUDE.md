# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

### Build and Run
- **Run the application**: `mvn spring-boot:run`
- **Build native image**: `mvn -Pnative spring-boot:build-image`
- **Run native image**: `docker run docker.io/library/phone-numbers-v1:0.0.1-SNAPSHOT`

### Testing
- **Run all tests**: `mvn clean test verify`
- **View coverage reports**: Open `target/site/jacoco/index.html` in browser after running tests
- **Coverage threshold**: 80% line coverage enforced by JaCoCo plugin

### Maven Wrapper
Use `./mvnw` instead of `mvn` if Maven is not globally installed.

## Architecture Overview

This is a Spring Boot 3.5.0 REST API for managing phone numbers with JDK 21 and virtual threads enabled.

### Key Components
- **Controller Layer**: `PhoneNumberController` handles REST endpoints
- **Service Layer**: `PhoneNumberService` contains business logic
- **Repository Layer**: `PhoneNumberRepository` handles data access using Spring Data JPA
- **Models**: Entity classes in `model.db` package, DTOs in `model.dto` package

### Database
- **In-memory H2 database** for persistence
- **Flyway migrations** in `src/main/resources/db/migration/`
- Sample data loaded via migration scripts

### API Design
- **Contract-first approach** using OpenAPI 3 specification
- API spec: `src/main/resources/static/api-docs.yaml`
- **Base path**: `/api/v1` (configured in application.properties)
- **Live documentation**: http://localhost:8080/api/v1/swagger-ui/index.html when running

### Endpoints
- **GET /phonenumbers**: Search with optional filtering by customer name and pagination
- **PATCH /phonenumbers/{subscriberNumber}**: Update phone number status

### Testing Strategy
- **Unit tests**: Service layer testing with mocks (`PhoneNumberServiceTest`)
- **Integration tests**: Full API testing with MockMvc (`PhoneNumbersApiIntegrationTest`)
- Uses JUnit 5, Mockito, and Spring Boot Test

### Dependencies
- Spring Boot Starter Web & JPA
- SpringDoc OpenAPI (v2.8.8) for API documentation
- Flyway for database migrations
- H2 Database for in-memory storage
- Hibernate Validator
- JaCoCo for test coverage reporting

## Development Notes

### Entity-DTO Mapping
The service layer handles mapping between JPA entities (`model.db`) and DTOs (`model.dto`). Manual mapping is currently used in `PhoneNumberService.mapToPhoneNumberDTO()`.

### Error Handling
Uses Spring's `ResponseStatusException` for HTTP error responses. Global exception handling is not implemented.

### Virtual Threads
Application uses virtual threads (`spring.threads.virtual.enabled=true`) for improved concurrency performance.
- use conventional commits when creating commit messages
- use ./mvnw instead of mvn when running maven commands