package com.telerikacademy.newgenerationpuppies.controllers;


import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.FilterChain;
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

        return "OK! " + r.isUserInRole("ROLE_UADMIN");
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
