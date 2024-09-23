# Full-Stack Application with Spring Boot and Flutter

This project showcases the development of a **full-stack application** using **Spring Boot** for the backend and **Flutter** for the frontend. It implements both monolithic and microservices architecture to provide a comprehensive solution following modern development practices such as data validation, security, and microservice management with **Spring Cloud**.

## Key Features

### Backend: Spring Boot Monolithic Application

1. **Entity Relationships**: 
   - Implements various relationships between entities, including **@OneToOne**, **@OneToMany**, **@ManyToOne**, and **@ManyToMany** annotations.

2. **CRUD Operations**: 
   - Provides complete **CRUD (Create, Read, Update, Delete)** operations for all entities.

3. **Database Profiling & Testing**:
   - Configured with multiple databases, including an in-memory database (**H2**) for testing purposes.
   - Supports testing profiles for different environments, with a separate database during the test phase.

4. **Testing**:
   - Includes **unit tests** and **integration tests** to validate business logic and overall application flow.

5. **Data Validation & Exception Handling**:
   - Implements **data validation** for form submissions and handles exceptions for better error management.

6. **Logging & Aspects**:
   - Provides detailed logging of important application events. Optionally integrates **aspects** for cross-cutting concerns.

7. **Pagination & Sorting**:
   - Implements **pagination** and **sorting** for efficient data retrieval and navigation.

8. **Spring Security**:
   - Secures the application using **Spring Security** with **JDBC-based authentication**.

---

### Microservices Architecture: Spring Cloud Backend

1. **Configuration Management**:
   - Utilizes **Spring Cloud Config Server** for centralized configuration management across all microservices.

2. **Feign REST Client**:
   - Implements **Feign** to simplify REST API communication between microservices.

3. **Service Discovery**:
   - Uses **Eureka Naming Server** for service registration and discovery among microservices.

4. **Load Balancing & Routing**:
   - Integrates **Cloud Load Balancer** for routing requests intelligently and balancing the load between services.

5. **Distributed Tracing**:
   - Monitors microservices and tracks distributed requests using **Zipkin Distributed Tracing Server**.

6. **Fault Tolerance**:
   - Ensures resilience with **Resilience4j** for handling failures and maintaining fault tolerance.

7. **API Documentation**:
   - Includes **Swagger** for automatically generating and maintaining API documentation.

8. **Error Handling**:
   - Centralized error handling is implemented for consistent and clear responses.

9. **HATEOAS**:
   - Adds **HATEOAS** links to the REST API responses for enhanced RESTful interactions.

10. **WebFlux**:
    - Includes **Spring WebFlux** for handling asynchronous and reactive programming requests.

---

### Frontend: Flutter

The frontend of this application is built using **Flutter**, a powerful UI toolkit for building **natively compiled** mobile and web applications from a single codebase. It allows for an efficient, responsive, and visually engaging user experience that seamlessly interacts with the Spring Boot backend via REST APIs.

---

## Technologies Used

- **Backend**: Spring Boot, Spring Cloud, Spring Security, H2 Database, MySQL, JPA/Hibernate
- **Frontend**: Flutter
- **Testing**: JUnit, Mockito
- **API Documentation**: Swagger
- **Microservices**: Eureka, Spring Cloud Config, Feign, Zipkin, Resilience4j, HATEOAS
- **Security**: Spring Security with JDBC Authentication
- **Database**: H2 (in-memory), MySQL
