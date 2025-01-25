# Task Management System

## Description and Objectives
The **Task Management System** is a web application designed for managing and organizing tasks efficiently. It provides functionality for creating, updating, and managing tasks, comments, and users with secure authentication and role-based access. The goal of this project is to develop a robust and scalable backend system that supports comprehensive task management workflows.

## Features

### Functionality for Unauthorized Users:
- Ability to register a new account.
- Access to Swagger UI documentation.

### Functionality for Authorized Users:
- **Authentication and Authorization:**
  - User login using JWT tokens.
  - Secure password storage with BCrypt encryption.
- **Task Management:**
  - Create, view, update, and delete tasks.
  - Assign tasks to specific users.
  - Add deadlines and priorities to tasks.
- **Comment Management:**
  - Add comments to tasks.
  - Edit and delete your own comments.
- **Pagination and Filtering:**
  - View tasks with support for pagination and filtering.

### Functionality for Administrators:
- **User Management:**
  - View and manage all user accounts.
- **Task and Comment Moderation:**
  - Edit and delete any task or comment in the system.

## Technologies Used
- **Programming Language:** Java 17+
- **Frameworks and Libraries:**
  - Spring Boot (including Spring Security, Spring Data JPA, and Spring Validation)
  - JJWT for token-based authentication
  - ModelMapper for object mapping
  - MapStruct for DTO conversion
  - SpringDoc for API documentation (Swagger UI)
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **Containerization:** Docker, Docker Compose
- **Others:** Lombok, BCryptPasswordEncoder

## How to Run the Application

### Prerequisites
- Install Java 17 or higher.
- Install Maven.
- Install Docker and Docker Compose.
- Configure a PostgreSQL database.

### Steps to Launch
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd task-management-system
   ```
3. Configure the database connection in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://<host>:<port>/<database>
   spring.datasource.username=<username>
   spring.datasource.password=<password>
   ```
4. Build the project:
   ```bash
   mvn clean install
   ```
5. Start the application using Docker Compose:
   ```bash
   docker-compose up
   ```
6. Access the application:
   - Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## API Endpoints

### Authentication
- `POST /auth/login`: Authenticate user and return JWT tokens.
- `POST /auth/register`: Register a new user.
- `POST /auth/refresh`: Refresh access and refresh tokens.

### Task Management
- `GET /tasks`: Retrieve tasks with filtering and pagination.
- `POST /tasks`: Create a new task.
- `PUT /tasks/{taskId}`: Update an existing task.
- `DELETE /tasks/{taskId}`: Delete a task.

### Comment Management
- `POST /tasks/{taskId}/comments`: Add a comment to a task.
- `PUT /comments/{commentId}`: Update a comment.
- `DELETE /comments/{commentId}`: Delete a comment.

## Security Configuration
- **JWT Authentication:**
  - Stateless session management with tokens.
  - Custom `JWTFilter` for token validation and setting authentication context.
  - Role-based access control with predefined roles (USER, ADMIN).
- **Password Encoding:**
  - BCryptPasswordEncoder for secure password storage.

### Open API Documentation
The application uses SpringDoc to provide interactive API documentation via Swagger UI.

## Project Team
- **Allan Allanazarov**: Developer



