package com.telerikacademy.newgenerationpuppies.controllers;

import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(){
        userRepository = new UserRepository();
    }

    @GetMapping("/users")
    public List<User> testMethod(){
        return userRepository.returnUsers();
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.saveUser(user);
    }

    @GetMapping("/success")
    public User returnUser(){
        return userRepository.giveUserKtb10();
    }

}
