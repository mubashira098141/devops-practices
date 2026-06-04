# Complete Spring Boot User CRUD API

A production-ready Spring Boot RESTful API for User management with MySQL database integration, comprehensive error handling, and validation.

## 📋 Features

✅ **Complete CRUD Operations**
- Create, Read, Update (Full & Partial), Delete
- Get user by ID or Email
- List all users

✅ **Validation & Error Handling**
- Input validation with detailed error messages
- Global exception handler
- Consistent error response format

✅ **Best Practices**
- RESTful API design
- Proper HTTP status codes
- Timestamps (createdAt, updatedAt)
- Unique email constraint
- CORS enabled

✅ **Database**
- MySQL 8 integration
- JPA/Hibernate ORM
- Automatic schema creation
- Audit timestamps

✅ **API Documentation**
- Comprehensive endpoint documentation
- Request/Response examples
- Error codes reference
- cURL examples

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- Docker & Docker Compose (Optional, for containerized setup)
- MySQL 8 (or use Docker)

### Option 1: Using Docker Compose (Recommended)

```bash
# Navigate to project directory
cd C:\userDemo\demo

# Start services
docker-compose up

# Stop services
docker-compose down
```

The API will be available at: `http://localhost:8085/api/v1/users`

### Option 2: Using Maven

```bash
# Navigate to project directory
cd C:\userDemo\demo

# Install dependencies and build
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be available at: `http://localhost:8083/api/v1/users`

### Option 3: Running JAR directly

```bash
cd C:\userDemo\demo

# Build first
mvn clean install

# Run JAR
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## 📚 API Endpoints

### Base URL
```
http://localhost:8085/api/v1/users
```

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/` | Create new user |
| GET | `/` | Get all users |
| GET | `/{id}` | Get user by ID |
| GET | `/email/{email}` | Get user by email |
| PUT | `/{id}` | Full update user |
| PATCH | `/{id}` | Partial update user |
| DELETE | `/{id}` | Delete user |
| HEAD | `/{id}` | Check if user exists |

## 📋 Request/Response Examples

### Create User

**Request:**
```bash
curl -X POST http://localhost:8085/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com"
  }'
```

**Response (201 Created):**
```json
{
  "message": "User created successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "createdAt": "2026-06-04T10:15:30",
    "updatedAt": "2026-06-04T10:15:30"
  }
}
```

### Get All Users

**Request:**
```bash
curl http://localhost:8085/api/v1/users
```

**Response:**
```json
{
  "message": "Users retrieved successfully",
  "count": 1,
  "data": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com",
      "createdAt": "2026-06-04T10:15:30",
      "updatedAt": "2026-06-04T10:15:30"
    }
  ]
}
```

### Update User (Full)

**Request:**
```bash
curl -X PUT http://localhost:8085/api/v1/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Doe",
    "email": "jane@example.com"
  }'
```

**Response:**
```json
{
  "message": "User updated successfully",
  "data": {
    "id": 1,
    "name": "Jane Doe",
    "email": "jane@example.com",
    "createdAt": "2026-06-04T10:15:30",
    "updatedAt": "2026-06-04T10:20:15"
  }
}
```

### Delete User

**Request:**
```bash
curl -X DELETE http://localhost:8085/api/v1/users/1
```

**Response:**
```json
{
  "message": "User deleted successfully"
}
```

### Error Response

**Request (Invalid Email):**
```bash
curl -X POST http://localhost:8085/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "J",
    "email": "invalid-email"
  }'
```

**Response (400 Bad Request):**
```json
{
  "timestamp": "2026-06-04T10:15:30",
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "name": "Name must be between 2 and 100 characters",
    "email": "Email should be valid"
  },
  "path": "/api/v1/users"
}
```

## 🧪 Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run with coverage
mvn jacoco:report
```

## 📁 Project Structure

```
demo/
├── src/
│   ├── main/
│   │   ├── java/com/user/demo/
│   │   │   ├── DemoApplication.java              # Main Spring Boot app
│   │   │   ├── controller/
│   │   │   │   └── userController.java           # REST endpoints
│   │   │   ├── service/
│   │   │   │   └── userService.java              # Business logic
│   │   │   ├── repository/
│   │   │   │   └── userRepository.java           # Data access layer
│   │   │   ├── entity/
│   │   │   │   └── User.java                     # JPA entity
│   │   │   ├── dto/
│   │   │   │   └── UserDTO.java                  # Data transfer object
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java   # Global exception handling
│   │   │       ├── ResourceNotFoundException.java
│   │   │       └── DuplicateEmailException.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/user/demo/
│           └── UserServiceTest.java              # Unit tests
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── API_DOCUMENTATION.md
└── README.md
```

## 🔧 Configuration

### application.properties

Key configurations in `src/main/resources/application.properties`:

```properties
# Server configuration
server.port=8083

# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3307/userdemo
spring.datasource.username=root
spring.datasource.password=root

# JPA/Hibernate configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Logging
logging.level.com.user.demo=DEBUG
```

### Docker Compose (docker-compose.yml)

- **MySQL**: Port 3307 (maps to 3306)
- **Spring Boot**: Port 8085 (maps to 8083)
- Database: `userdemo`
- Credentials: root/root

## 📊 Database Schema

```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## ✅ Validation Rules

### User Entity

| Field | Rules |
|-------|-------|
| **name** | Required, 2-100 characters |
| **email** | Required, valid email format, unique |

## 🔐 HTTP Status Codes

| Code | Meaning |
|------|---------|
| **200** | OK - Request successful |
| **201** | Created - Resource created |
| **400** | Bad Request - Invalid input/validation failed |
| **404** | Not Found - Resource not found |
| **409** | Conflict - Duplicate email |
| **500** | Internal Server Error |

## 🛠 Tech Stack

- **Framework**: Spring Boot 4.0.6
- **Language**: Java 17
- **Database**: MySQL 8
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **Validation**: Hibernate Validator
- **Testing**: JUnit 5

## 📦 Dependencies

See `pom.xml` for all dependencies:

- spring-boot-starter-data-jpa
- spring-boot-starter-webmvc
- spring-boot-starter-validation
- mysql-connector-j
- spring-boot-starter-test

## 🔄 Development Workflow

1. **Code Changes**: Edit files in `src/main/java`
2. **Build**: `mvn clean install`
3. **Test**: `mvn test`
4. **Run**: `mvn spring-boot:run` or `docker-compose up`
5. **API Test**: Use cURL, Postman, or REST client

## 🐛 Troubleshooting

### Port Already in Use
If port 8085 is in use:
1. Update `docker-compose.yml` or `application.properties`
2. Or stop the service using the port

### Database Connection Error
```
Ensure MySQL is running and accessible:
- docker-compose up (for MySQL in container)
- Check credentials in application.properties
```

### Build Failures
```bash
# Clean Maven cache
mvn clean
# Update dependencies
mvn dependency:resolve
# Rebuild
mvn install
```

## 📞 API Testing Tools

### Using cURL
```bash
# Create user
curl -X POST http://localhost:8085/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com"}'
```

### Using Postman
1. Import endpoints from API_DOCUMENTATION.md
2. Set Content-Type: application/json
3. Test each endpoint

### Using Thunder Client (VS Code)
1. Create requests from endpoint examples
2. Test and debug

## 📝 Notes

- Email validation is case-insensitive in queries
- Timestamps are automatically managed by JPA
- All errors return consistent JSON format
- CORS is enabled for all origins
- Database auto-creates/updates schema on startup

## 📄 License

This project is open source and available for educational use.

---

**Happy Coding! 🚀**

For more details, see [API_DOCUMENTATION.md](./API_DOCUMENTATION.md)

