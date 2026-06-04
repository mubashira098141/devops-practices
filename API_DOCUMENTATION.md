# User CRUD API Documentation

This is a complete Spring Boot RESTful API for User management with MySQL database integration.

## Base URL
```
http://localhost:8085/api/v1/users
```

## Endpoints

### 1. Create a New User
**Endpoint:** `POST /api/v1/users`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
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

**Error Response (400 Bad Request):**
```json
{
  "timestamp": "2026-06-04T10:15:30",
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "email": "Email already exists: john.doe@example.com"
  },
  "path": "/api/v1/users"
}
```

---

### 2. Get All Users
**Endpoint:** `GET /api/v1/users`

**Response (200 OK):**
```json
{
  "message": "Users retrieved successfully",
  "count": 2,
  "data": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "john.doe@example.com",
      "createdAt": "2026-06-04T10:15:30",
      "updatedAt": "2026-06-04T10:15:30"
    },
    {
      "id": 2,
      "name": "Jane Smith",
      "email": "jane.smith@example.com",
      "createdAt": "2026-06-04T10:20:45",
      "updatedAt": "2026-06-04T10:20:45"
    }
  ]
}
```

---

### 3. Get User by ID
**Endpoint:** `GET /api/v1/users/{id}`

**Example:** `GET /api/v1/users/1`

**Response (200 OK):**
```json
{
  "message": "User retrieved successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "createdAt": "2026-06-04T10:15:30",
    "updatedAt": "2026-06-04T10:15:30"
  }
}
```

**Error Response (404 Not Found):**
```json
{
  "timestamp": "2026-06-04T10:15:30",
  "status": 404,
  "message": "User not found with id: 999",
  "path": "/api/v1/users/999"
}
```

---

### 4. Get User by Email
**Endpoint:** `GET /api/v1/users/email/{email}`

**Example:** `GET /api/v1/users/email/john.doe@example.com`

**Response (200 OK):**
```json
{
  "message": "User retrieved successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "createdAt": "2026-06-04T10:15:30",
    "updatedAt": "2026-06-04T10:15:30"
  }
}
```

---

### 5. Update User (Full Update)
**Endpoint:** `PUT /api/v1/users/{id}`

**Example:** `PUT /api/v1/users/1`

**Request Body:**
```json
{
  "name": "John Smith",
  "email": "john.smith@example.com"
}
```

**Response (200 OK):**
```json
{
  "message": "User updated successfully",
  "data": {
    "id": 1,
    "name": "John Smith",
    "email": "john.smith@example.com",
    "createdAt": "2026-06-04T10:15:30",
    "updatedAt": "2026-06-04T10:30:15"
  }
}
```

---

### 6. Partial Update User
**Endpoint:** `PATCH /api/v1/users/{id}`

**Example:** `PATCH /api/v1/users/1`

**Request Body (Only update name):**
```json
{
  "name": "John Updated"
}
```

**Response (200 OK):**
```json
{
  "message": "User patched successfully",
  "data": {
    "id": 1,
    "name": "John Updated",
    "email": "john.smith@example.com",
    "createdAt": "2026-06-04T10:15:30",
    "updatedAt": "2026-06-04T10:35:00"
  }
}
```

---

### 7. Delete User
**Endpoint:** `DELETE /api/v1/users/{id}`

**Example:** `DELETE /api/v1/users/1`

**Response (200 OK):**
```json
{
  "message": "User deleted successfully"
}
```

**Error Response (404 Not Found):**
```json
{
  "timestamp": "2026-06-04T10:15:30",
  "status": 404,
  "message": "User not found with id: 999",
  "path": "/api/v1/users/999"
}
```

---

### 8. Check if User Exists
**Endpoint:** `HEAD /api/v1/users/{id}`

**Example:** `HEAD /api/v1/users/1`

**Response (200 OK):** Empty body with HTTP 200

**Response (404 Not Found):** Empty body with HTTP 404

---

## Validation Rules

### User Entity Validation:
- **Name:**
  - Required
  - Minimum 2 characters
  - Maximum 100 characters

- **Email:**
  - Required
  - Must be a valid email format
  - Must be unique in the database

---

## Error Codes

| Status Code | Description |
|---|---|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 400 | Bad Request - Invalid input or validation failed |
| 404 | Not Found - Resource not found |
| 409 | Conflict - Duplicate email |
| 500 | Internal Server Error - Server error |

---

## Database Schema

### users table
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## How to Run

### Using Docker Compose:
```bash
docker-compose up
```

This will start:
- MySQL database on port 3307
- Spring Boot API on port 8085

### Using Maven:
```bash
mvn clean install
mvn spring-boot:run
```

### Direct JAR:
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

---

## Example cURL Commands

### Create User:
```bash
curl -X POST http://localhost:8085/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'
```

### Get All Users:
```bash
curl http://localhost:8085/api/v1/users
```

### Get User by ID:
```bash
curl http://localhost:8085/api/v1/users/1
```

### Update User:
```bash
curl -X PUT http://localhost:8085/api/v1/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Doe","email":"jane@example.com"}'
```

### Delete User:
```bash
curl -X DELETE http://localhost:8085/api/v1/users/1
```

---

## Technology Stack
- **Framework:** Spring Boot 4.0.6
- **Database:** MySQL 8
- **ORM:** JPA/Hibernate
- **Language:** Java 17
- **Build Tool:** Maven
- **Containerization:** Docker & Docker Compose

---

## Project Structure
```
demo/
├── src/
│   ├── main/
│   │   ├── java/com/user/demo/
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── service/         # Business logic
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── entity/          # JPA entities
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Custom exceptions & handler
│   │   │   └── DemoApplication.java  # Main class
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

