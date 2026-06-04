# Complete Debugging Guide

## 📍 Add Debug Statements

Example in `userService.java`:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class userService {
    private static final Logger logger = LoggerFactory.getLogger(userService.class);
    
    @Autowired
    private userRepository userRepository;

    public User createUser(User user) {
        logger.debug("Creating user with email: {}", user.getEmail());
        
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("Duplicate email attempt: {}", user.getEmail());
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }
        
        User savedUser = userRepository.save(user);
        logger.info("User created successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    public User getUserById(Long id) {
        logger.debug("Fetching user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", id);
                    return new RuntimeException("User not found with id: " + id);
                });
        logger.info("User found: {}", user);
        return user;
    }
}
```

---

## 🔍 Debug with cURL

### **Test GET Request with Verbose Output:**
```bash
curl -v http://localhost:8085/api/v1/users
```

Output shows:
```
* Connected to localhost (127.0.0.1) port 8085
* Connected to localhost port 8085
> GET /api/v1/users HTTP/1.1
> User-Agent: curl/7.68.0
> Host: localhost:8085
> Accept: */*
<
< HTTP/1.1 200
< Content-Type: application/json
< Content-Length: 123
```

### **Test POST with Headers:**
```bash
curl -v -X POST http://localhost:8085/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@example.com"}'
```

### **Test with Error Scenarios:**
```bash
# Missing required field
curl -X POST http://localhost:8085/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Test"}'

# Invalid email format
curl -X POST http://localhost:8085/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"invalid"}'

# Get non-existent user
curl -v http://localhost:8085/api/v1/users/999
```

---

## 📊 Check Logs in Real-Time

When running with `mvn spring-boot:run`, logs appear in terminal. Look for:

### **Success Logs:**
```
2026-06-04T10:15:35.456+05:30  INFO 1234 --- User created successfully with ID: 1
```

### **Error Logs:**
```
2026-06-04T10:15:40.789+05:30  ERROR 1234 --- User not found with ID: 999
```

### **Debug Logs:**
```
2026-06-04T10:15:35.123+05:30 DEBUG 1234 --- Creating user with email: test@example.com
```

---

## 🷹 H2 Database Console (In-Memory DB for Testing)

Would be setup like this in `pom.xml`:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

And in `application.properties`:
```properties
spring.h2.console.enabled=true
# Access at: http://localhost:8083/h2-console
```

---

## 🔧 Maven Debug Mode

Run with debug output:
```bash
mvn -X spring-boot:run
```

Shows all Maven internals and dependency resolution.

---

## 📝 Postman / REST Client Testing

### **Test Endpoints Systematically:**

1. **Create User**
   - Method: POST
   - URL: `http://localhost:8085/api/v1/users`
   - Body: `{"name":"John","email":"john@test.com"}`
   - Expected: 201 Created

2. **Get All Users**
   - Method: GET
   - URL: `http://localhost:8085/api/v1/users`
   - Expected: 200 OK with list

3. **Get User by ID**
   - Method: GET
   - URL: `http://localhost:8085/api/v1/users/1`
   - Expected: 200 OK or 404 if not found

4. **Update User**
   - Method: PUT
   - URL: `http://localhost:8085/api/v1/users/1`
   - Body: `{"name":"Updated","email":"updated@test.com"}`
   - Expected: 200 OK

5. **Delete User**
   - Method: DELETE
   - URL: `http://localhost:8085/api/v1/users/1`
   - Expected: 200 OK

---

## 📊 Monitor Application Stats

### **Check Running Processes:**
```bash
# Windows PowerShell
Get-Process java | Select-Object ProcessName, Handles, WorkingSet

# Output:
# Handles  ProcessName  WorkingSet
# -------  -----------  ----------
# 1234     java         456789
```

### **Check Port Usage:**
```bash
# Windows
netstat -ano | findstr ":8085"

# Output:
# TCP    127.0.0.1:8085    LISTENING    12345
```

---

## 🔴 Common Issues & Debug Steps

### **Issue: "Connection refused"**
```
Check: Is the app running?
Debug: mvn spring-boot:run
Verify: http://localhost:8085/api/v1/users
```

### **Issue: "Database connection error"**
```
Check: Is MySQL running?
Debug: Show error logs
Fix: Start MySQL or use docker-compose
```

### **Issue: "Cannot find entity"**
```
Check: Did you create the user?
Debug: GET /api/v1/users to list all
Verify: Try with ID from response
```

### **Issue: "Invalid email"**
```
Check: Email format is valid?
Debug: Validation error in response
Fix: Use proper email format
```

---

## 🚀 Quick Start Debugging

### **Step 1: Start with Console Logs**
```bash
cd C:\userDemo\demo
mvn spring-boot:run
```

### **Step 2: Test in Another Terminal**
```bash
# Create user
curl -X POST http://localhost:8085/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Debug Test","email":"debug@test.com"}'

# Check logs in first terminal for output
```

### **Step 3: Review Response**
```json
{
  "message": "User created successfully",
  "data": {
    "id": 1,
    "name": "Debug Test",
    "email": "debug@test.com",
    "createdAt": "2026-06-04T10:15:35",
    "updatedAt": "2026-06-04T10:15:35"
  }
}
```

### **Step 4: If Error, Check Logs**
Look for RED/ERROR lines in console output for debugging info.

---

## 📋 Debug Checklist

- ✅ Enable debug logging in `application.properties`
- ✅ Use `logger.debug()` statements in code
- ✅ Test with `curl -v` for verbose output
- ✅ Check console output for errors
- ✅ Verify database connection
- ✅ Test all endpoints with cURL/Postman
- ✅ Check port 8085 is not in use
- ✅ Review error response messages

---

**Pro Tip**: Always check the **console logs first** - they usually tell you exactly what's wrong! 🎯

