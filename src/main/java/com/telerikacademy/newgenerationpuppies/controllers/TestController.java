package com.telerikacademy.newgenerationpuppies.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {


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

}
