package com.telerikacademy.newgenerationpuppies.controllers;

import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import com.telerikacademy.newgenerationpuppies.repos.adminrepository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository){

        this.adminRepository = adminRepository;
    }

    @PostMapping("/registeruser")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public String registerUser(@RequestBody User user){
        String role = "ROLE_USER";
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return adminRepository.saveUser(user, role);
    }

    @PostMapping("/registeradmin")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public String registerAdmin(@RequestBody User user){
        String role = "ROLE_ADMIN";
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return adminRepository.saveUser(user, role);
    }

}
