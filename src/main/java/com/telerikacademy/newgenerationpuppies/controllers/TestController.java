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
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    private UserRepositoryImpl userRepositoryImpl;

    public TestController(UserRepositoryImpl userRepository){
        this.userRepositoryImpl = userRepository;
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

    @PutMapping("/paybill/{id}")
    public Bill payBill(@PathVariable int id){
        return userRepositoryImpl.payBill(id);
    }

    @GetMapping
    public String returnModel(HttpServletRequest httpServletRequest){

        return userRepositoryImpl.test(httpServletRequest);
    }

    @GetMapping("/h")
    public List<Map<String, String>> history(HttpServletRequest httpServletRequest){
        return userRepositoryImpl.history(httpServletRequest);
    }

}
