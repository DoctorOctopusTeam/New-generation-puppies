package com.telerikacademy.newgenerationpuppies.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {
    @GetMapping("/login")
    public String showHomepage(){
        return "login";
    }


    @GetMapping("/home")
    public String srhowHomepage(){
        return "home";
    }
}
