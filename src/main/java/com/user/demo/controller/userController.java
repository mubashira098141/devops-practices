package com.user.demo.controller;

import com.user.demo.entity.User;
import com.user.demo.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class userController {
    @Autowired
    private userService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.savedUser(user);

    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();

    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}

