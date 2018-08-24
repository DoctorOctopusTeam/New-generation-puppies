package com.telerikacademy.newgenerationpuppies.controllers;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import com.telerikacademy.newgenerationpuppies.service.UserService;
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

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


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
        return userService.getSubscriberInfo(phoneNumber, httpServletRequest);
    }

    //DONE
    //A client should be able to see a history of the payments for its subscribers sorted descending by the date of payment
    @GetMapping("user/payments")
    public List<Bill> getAllPayments(HttpServletRequest httpServletRequest){
        return userService.getAllPayments(httpServletRequest);
    }

    //A client should be able to see the average and MAX amount of money payed for a subscriber for a defined period of time
    //TODO - define the time period
    @GetMapping("user/reports/max/{phoneNumber}")
    public Bill getMaxPayedFromSubscriber(@PathVariable int phoneNumber, HttpServletRequest httpServletRequest){
        return userService.getMaxPayedFromSubscriber(phoneNumber, httpServletRequest);
    }

    //A client should be able to see the AVERAGE and max amount of money payed for a subscriber for a defined period of time
    //TODO - define the time period
    @GetMapping("user/reports/average/{phoneNumber}")
    public HashMap<String, Double> getAveragePayedFromSubscriber(@PathVariable int phoneNumber, HttpServletRequest httpServletRequest){
        return userService.getAveragePayedFromSubscriber(phoneNumber, httpServletRequest);
    }

    //A client should be able to see a list of the services the client has paid for
    @GetMapping("user/services/{phoneNumber}")
    public List<String> usedServicesFromSubscriber(@PathVariable int phoneNumber, HttpServletRequest httpServletRequest){
        return null;
    }

    //Clients must have access to bill payment module where they can pay a particular bill (or selected list of bills)
    // for their subscribers
    @PutMapping("user/pay")
    public Bill payBill(){
        return null;
    }

    //Client can see report - Top 10 subscribers with the biggest amount of money payed.
    @GetMapping("user/reports/10biggest-amounts")
    public List<Subscriber> getBiggestAmountsPayedBySubscribers(HttpServletRequest httpServletRequest){
        return userService.getBiggestAmountsPayedBySubscribers(httpServletRequest);
    }

}
