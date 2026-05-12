package com.user.demo.service;

import com.user.demo.entity.User;
import com.user.demo.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {
    @Autowired
    private userRepository userRepository;

    public User savedUser(User  user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository. findAll();

    }
    public User getUser(Long id){
        return userRepository.findById(id).get();

    }
    public String deleteUser(Long id){
        userRepository.deleteById(id);
        return "Deleted";
    }

}
