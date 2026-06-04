package com.user.demo;

import com.user.demo.entity.User;
import com.user.demo.repository.userRepository;
import com.user.demo.service.userService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class UserServiceTest {

    @Autowired
    private userService userService;

    @Autowired
    private userRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Clear all users before each test
        userRepository.deleteAll();
    }

    @Test
    public void testCreateUser() {
        User user = new User("John Doe", "john@example.com");
        User createdUser = userService.createUser(user);

        assertNotNull(createdUser.getId());
        assertEquals("John Doe", createdUser.getName());
        assertEquals("john@example.com", createdUser.getEmail());
    }

    @Test
    public void testCreateUserWithDuplicateEmail() {
        User user1 = new User("John Doe", "john@example.com");
        userService.createUser(user1);

        User user2 = new User("Jane Doe", "john@example.com");
        assertThrows(RuntimeException.class, () -> userService.createUser(user2));
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User("John Doe", "john@example.com");
        User user2 = new User("Jane Smith", "jane@example.com");
        
        userService.createUser(user1);
        userService.createUser(user2);

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserById() {
        User user = new User("John Doe", "john@example.com");
        User createdUser = userService.createUser(user);

        User retrievedUser = userService.getUserById(createdUser.getId());
        assertEquals("John Doe", retrievedUser.getName());
        assertEquals("john@example.com", retrievedUser.getEmail());
    }

    @Test
    public void testGetUserByIdNotFound() {
        assertThrows(RuntimeException.class, () -> userService.getUserById(999L));
    }

    @Test
    public void testGetUserByEmail() {
        User user = new User("John Doe", "john@example.com");
        userService.createUser(user);

        User retrievedUser = userService.getUserByEmail("john@example.com");
        assertEquals("John Doe", retrievedUser.getName());
    }

    @Test
    public void testGetUserByEmailNotFound() {
        assertThrows(RuntimeException.class, () -> userService.getUserByEmail("nonexistent@example.com"));
    }

    @Test
    public void testUpdateUser() {
        User user = new User("John Doe", "john@example.com");
        User createdUser = userService.createUser(user);

        User updateData = new User("Jane Doe", "jane@example.com");
        User updatedUser = userService.updateUser(createdUser.getId(), updateData);

        assertEquals("Jane Doe", updatedUser.getName());
        assertEquals("jane@example.com", updatedUser.getEmail());
    }

    @Test
    public void testUpdateUserPartial() {
        User user = new User("John Doe", "john@example.com");
        User createdUser = userService.createUser(user);

        User updateData = new User();
        updateData.setName("Jane Doe");
        
        User updatedUser = userService.updateUser(createdUser.getId(), updateData);
        assertEquals("Jane Doe", updatedUser.getName());
        assertEquals("john@example.com", updatedUser.getEmail());
    }

    @Test
    public void testUpdateUserNotFound() {
        User updateData = new User("Jane Doe", "jane@example.com");
        assertThrows(RuntimeException.class, () -> userService.updateUser(999L, updateData));
    }

    @Test
    public void testDeleteUser() {
        User user = new User("John Doe", "john@example.com");
        User createdUser = userService.createUser(user);

        userService.deleteUser(createdUser.getId());
        assertFalse(userService.userExists(createdUser.getId()));
    }

    @Test
    public void testDeleteUserNotFound() {
        assertThrows(RuntimeException.class, () -> userService.deleteUser(999L));
    }

    @Test
    public void testUserExists() {
        User user = new User("John Doe", "john@example.com");
        User createdUser = userService.createUser(user);

        assertTrue(userService.userExists(createdUser.getId()));
        assertFalse(userService.userExists(999L));
    }
}

