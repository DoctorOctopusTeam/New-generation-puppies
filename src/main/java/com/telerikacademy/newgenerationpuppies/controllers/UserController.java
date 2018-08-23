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

//    @GetMapping("/users")
//    public List<User> testMethod(){
//        return userRepository.returnUsers();
//    }

//    @PostMapping("/register")
//    public void registerUser(@RequestBody User user){
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//
//        userRepository.saveUser(user);
//    }

//    @GetMapping("/success")
//    public User returnUser(){
//        return userRepository.giveUserKtb10();
//    }

    //DONE
    //The client must be able to see personal details of a subscriber
    @GetMapping("/user/info/{phoneNumber}")
    public HashMap<String, String> getInfoSubscriber(@PathVariable int phoneNumber, HttpServletRequest httpServletRequest) {
        return userRepository.getSubscriberInfo(phoneNumber, httpServletRequest);
    }

    //DONE
    //A client should be able to see a history of the payments for its subscribers sorted descending by the date of payment
    @GetMapping("user/payments")
    public List<Bill> getAllPayments(HttpServletRequest httpServletRequest){
        return userRepository.getAllPayments(httpServletRequest);
    }

    //A client should be able to see the average and max amount of money payed for a subscriber for a defined period of time
    @GetMapping("user/reports/{phoneNumber}")
    public String getAverageMaxPayedFromSubscriber(@PathVariable int phoneNumber, HttpServletRequest httpServletRequest){
        return null;
    }

    //A client should be able to see a list of the services the client has paid for
    @GetMapping("user/services/{phoneNumber}")
    public List<String> usedServicesFromSubsciber(@PathVariable int phoneNumber, HttpServletRequest httpServletRequest){
        return null;
    }

    //Clients must have access to bill payment module where they can pay a particular bill (or selected list of bills) for their subscribers
    @PutMapping("user/pay")
    public Bill payBill(){
        return null;
    }

}
