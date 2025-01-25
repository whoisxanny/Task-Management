Task Management System

Description and Objectives

The Task Management System is a web application designed to help users manage tasks effectively. It provides a platform for users to create, manage, and track tasks, as well as collaborate with others through role-based access controls. The primary objective is to implement a secure, scalable, and user-friendly application that simplifies task management for individuals and organizations.

Key Features

Functionality for Unauthorized Users:

Access public endpoints such as authentication and API documentation.

Functionality for Authorized Users:

User registration and login using JWT-based authentication.

Create, update, and delete tasks with associated details.

Add, edit, and delete comments on tasks.

View and filter tasks with pagination support.

Functionality for Administrators:

Manage user roles and permissions.

Edit or delete any user's comments or tasks.

Launching the Application

Prerequisites:

Java 17+

Maven

Docker and Docker Compose

Steps:

Clone the repository:

git clone <repository-url>

Configure the database connection:
Update the application.properties file with your PostgreSQL or MySQL credentials.

Run the application with Docker Compose:

docker-compose up --build

Access the application:

Swagger UI: http://localhost:8080/swagger-ui/index.html

Technologies Used

Java 17: Core programming language

Spring Boot 3.4.1: Framework for building the application

Spring Security: Handles authentication and authorization

Spring JPA: Manages database interactions

PostgreSQL: Database for storing application data

Docker Compose: Orchestrates multi-container deployments

JWT (Json Web Tokens): Secures endpoints and manages user sessions

Springdoc (OpenAPI): API documentation

Mapstruct: Simplifies object mapping

ModelMapper: Enhances data transformations

Lombok: Reduces boilerplate code

JUnit: Unit testing framework

Project Structure

Security Features:

Stateless JWT-based authentication with JWTProvider and JWTFilter.

Role-based access control for fine-grained permissions.

Protection against unauthorized and forbidden access with custom handlers.

API Endpoints:

Authentication: auth/** endpoints for login and registration.

Task Management: Endpoints for managing tasks and comments.

Swagger Documentation: swagger-ui/** and /v3/api-docs/**.

Development Details

POM Highlights

Key dependencies:

Spring Boot Starter dependencies (Web, Data JPA, Security, Validation)

PostgreSQL driver

Springdoc for OpenAPI documentation

JWT (jjwt-api, jjwt-impl, jjwt-jackson)

Mapstruct and ModelMapper

Security Configuration:

Stateless sessions with Spring Security.

CSRF protection disabled to support RESTful architecture.

AuthenticationManager configured with BCryptPasswordEncoder.

Custom JWTFilter integrated into the security filter chain.

Project Team

[Your Name], Developer

