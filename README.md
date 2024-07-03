# Task Management with Security

This project is a task management application built with Spring Boot, featuring robust security mechanisms including JWT authentication and error handling. The application is structured to ensure scalability, maintainability, and ease of development.

## Project Structure

The project follows a well-organized package structure:

- **com.victor.task_management_with_security**
  - **auditor**
    - `AppAuditAware`
  - **config**
    - `AppConfig`
    - `AppLogoutHandler`
    - `SwaggerConfig`
  - **controller**
    - `AuthController`
    - `TaskController`
    - `UserController`
  - **dto**
    - **auth**
      - `AuthResponse`
      - `LoginResponse`
      - `RegisterResponse`
    - **task**
      - `CreateTaskRequest`
      - `EditTaskRequest`
      - `TaskResponse`
    - **user**
      - `ProfileEditRequest`
      - `UserResponse`
  - **entity**
    - `Task`
    - `Token`
    - `User`
  - **exception**
    - `EmailAlreadyExistsException`
    - `InvalidDateFormatException`
    - `NotPermittedException`
  - **exception_handler**
    - `ExceptionResponse`
    - `GlobalExceptionHandler`
  - **jwt**
    - `JwtFilter`
  - **mapper**
    - `TaskMapper`
    - `UserMapper`
  - **repository**
    - `TaskRepository`
    - `TokenRepository`
    - `UserRepository`
  - **security**
    - `CustomUserDetailsService`
    - `SecurityConfig`
  - **service**
    - **auth**
      - `AuthenticationService`
    - **jwt**
      - `JwtService`
    - **task**
      - `TaskService`
    - **token**
      - `TokenService`
    - **user**
      - `UserService`
  - **utils**
    - `PageResponse`

## Features

### Authentication
- **JWT Authentication:** Secure login and registration with JWT tokens.
- **Well secured token management:** Special attention is placed to making sure unused tokens are not littered around. Only the most recently generated token is enabled. Other are revoked to maintain higher security.
- **Log out:** A reliable log out feature.
  
### Task Management
- **Create, Update, and Delete Tasks:** Users can manage their tasks with full CRUD functionality.
- **Task Status Update:** Tasks can be marked as complete or incomplete.
- **Task Filtering and Sorting:** Advanced and highly customizable task filtering and sorting options are available based on various criteria such as due date and status.
- **Pagination**

### User Management
- **User Profiles:** Users can view and update their profile information.
- **User Listing:** Users can view a list of all users.

### Exception Handling and Validation
- **Global Exception Handling:** Global exception handling strategy to provide meaningful error messages and HTTP statuses.

### API Documentation
- **Swagger Integration:** Interactive API documentation using Swagger.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- PostgreSQL (or another preferred database)

### Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/victoradepoju/TaskManagementPrunny.git
    cd task_management_with_security
    ```

2. **Configure the database:**
   Update the `application-dev.yml` file with your database configuration:
    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/yourdatabase
        username: yourusername
        password: yourpassword
      jpa:
        hibernate:
          ddl-auto: update
    ```

3. **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

4. **Access the API documentation:**
   Visit `http://localhost:8080/swagger-ui/index.html` to view the interactive API documentation.

### Using Docker

To simplify the setup process, you can use Docker and Docker Compose to manage your dependencies.

1. **Create a `docker-compose.yml` file in the root directory:**

    ```yaml
    services:
      postgres:
        container_name: your_container_name
        image: postgres
        environment:
          POSTGRES_USER: your_username
          POSTGRES_PASSWORD: your_password
          PGDATA: /var/lib/postgresql/data
          POSTGRES_DB: your_database
        volumes:
          - postgres:/data/postgres
        ports:
          - 5432:5432
        networks:
          - network_name
        restart: unless-stopped

    networks:
      network_name:
        driver: bridge
    volumes:
      postgres:
        driver: local
    ```

2. **Run Docker Compose:**

    ```bash
    docker-compose up -d
    ```

   This command will start a PostgreSQL container with the specified configuration.

3. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

   Your application should now be able to connect to the PostgreSQL database running in the Docker container.

## Usage

### Authentication

- **Register:**
    ```
    POST /auth/register
    {
        "firstname": "Victor",
        "lastname": "Dev",
        "email": "victor.dev@example.com",
        "password": "password123"
    }
    ```

- **Login:**
    ```
    POST /auth/login
    {
        "email": "victor.dev@example.com",
        "password": "password123"
    }
    ```

### Task Management

- **Create Task:**
    ```
    POST /task
    {
        "title": "New Task",
        "description": "Task description",
        "dueDate": "2023-07-10"
    }
    ```

- **Edit Task:**
    ```
    PATCH /task/{taskId}
    {
        "title": "Updated Task Title",
        "description": "Updated Task Description",
        "dueDate": "2023-07-15"
    }
    ```

- **Update Task Status:**
    ```
    PATCH /task/status/{taskId}
    ```

- **Delete Task:**
    ```
    DELETE /task/delete/{taskId}
    ```

- **Find Task by ID:**
    ```
    GET /task/{taskId}
    ```

- **Find All Tasks:**
    ```
    GET /task?page=0&size=10&sortByStatus=true&sortByDueDate=false&filterByStatus=false&startDate=2023-07-01&endDate=2023-07-31
    ```
    You can play with the parameters to filter and sort.

### User Management

- **Find All Users:**
    ```
    GET /users?page=0&size=10
    ```

- **Find User by ID:**
    ```
    GET /users/{userId}
    ```

- **User Profile:**
    ```
    GET /users/profile
    ```

- **Edit User Profile:**
    ```
    PATCH /users/profile/{userId}
    {
        "firstName": "Updated Firstname",
        "lastName": "Updated Lastname"
    }
    ```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
