package com.telerikacademy.newgenerationpuppies.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
    @GetMapping
    public String start(){
        return "Successfully started the process";
    }


}
