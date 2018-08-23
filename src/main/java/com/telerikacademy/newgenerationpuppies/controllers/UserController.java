package com.telerikacademy.newgenerationpuppies.controllers;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository){

        this.userRepository = userRepository;
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

    @GetMapping("/user/info/{phoneNumber}")
    public HashMap<String, String> getInfoSubscriber(@PathVariable int phoneNumber, HttpServletRequest httpServletRequest) {
        return userRepository.getSubscriberInfo(phoneNumber, httpServletRequest);
    }

    @GetMapping("user/payments")
    public List<Bill> getAllPayments(HttpServletRequest httpServletRequest){

        return userRepository.getAllPayments(httpServletRequest);
    }

}
