package com.telerikacademy.newgenerationpuppies.controllers;


import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;



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

    @GetMapping("/four")
    public String testFour(HttpServletRequest r){
        Timestamp time = new Timestamp(7);
        String a = time.toString();
        return a;
    }

}
