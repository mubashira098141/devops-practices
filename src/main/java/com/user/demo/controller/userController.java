package com.user.demo.controller;

import com.user.demo.entity.User;
import com.user.demo.service.userService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class userController {
    @Autowired
    private userService userService;

    /**
     * Create a new user
     * POST /api/v1/users
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "User created successfully",
                            "data", createdUser
                    ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "error", e.getMessage()
                    ));
        }
    }

    /**
     * Get all users
     * GET /api/v1/users
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(Map.of(
                "message", "Users retrieved successfully",
                "count", users.size(),
                "data", users
        ));
    }

    /**
     * Get user by ID
     * GET /api/v1/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(Map.of(
                    "message", "User retrieved successfully",
                    "data", user
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", e.getMessage()
                    ));
        }
    }

    /**
     * Get user by email
     * GET /api/v1/users/email/{email}
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(Map.of(
                    "message", "User retrieved successfully",
                    "data", user
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", e.getMessage()
                    ));
        }
    }

    /**
     * Update an existing user
     * PUT /api/v1/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(Map.of(
                    "message", "User updated successfully",
                    "data", updatedUser
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", e.getMessage()
                    ));
        }
    }

    /**
     * Partially update a user
     * PATCH /api/v1/users/{id}
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> patchUser(
            @PathVariable Long id,
            @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(Map.of(
                    "message", "User patched successfully",
                    "data", updatedUser
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", e.getMessage()
                    ));
        }
    }

    /**
     * Delete a user
     * DELETE /api/v1/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(Map.of(
                    "message", "User deleted successfully"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "error", e.getMessage()
                    ));
        }
    }

    /**
     * Check if user exists
     * HEAD /api/v1/users/{id}
     */
    @RequestMapping(method = org.springframework.web.bind.annotation.RequestMethod.HEAD, value = "/{id}")
    public ResponseEntity<Void> userExists(@PathVariable Long id) {
        if (userService.userExists(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
