package com.telerikacademy.newgenerationpuppies.controllers;

import com.telerikacademy.newgenerationpuppies.models.Authority;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import com.telerikacademy.newgenerationpuppies.repos.UserRepositoryImpl;
import org.hibernate.Session;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    private UserRepository userRepository;

    public TestController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @GetMapping("/one")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public String test(HttpServletRequest r){

        return "OK! " + r.getUserPrincipal().getName() + r.getUserPrincipal();
    }

    @GetMapping("/two")
    public String testTwo(HttpServletRequest r){

        return "OK! " + r.getUserPrincipal().getName()+ r.getUserPrincipal();
    }

    @GetMapping("/three")
    public String testThree(HttpServletRequest r){

        return "OK! " + r.getUserPrincipal().getName()+ r.getUserPrincipal();
    }

    
    //--------------------------------------------------

//    @PutMapping("/paybill/{id}")
//    public Bill payBill(@PathVariable int id){
//        return userRepository.payBill(id);
//    }
//
//    @GetMapping
//    public User returnModel(){
//        return userRepository.test();
//    }

}
