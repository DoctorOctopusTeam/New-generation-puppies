package com.telerikacademy.newgenerationpuppies.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import com.telerikacademy.newgenerationpuppies.repos.adminrepository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @PutMapping("/changepassword")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String repeatNewPassword,
                                 HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();
        User user = adminRepository.findUser(nameOfBank);
        String oldOne = user.getPassword();
        boolean isTrue = bCryptPasswordEncoder.matches(oldPassword, oldOne);
        if(!isTrue){
            return "Your current password is different than what you have entered!";
        }
        if(!newPassword.equals(repeatNewPassword)){
            return "There is a mismatch between the password and the repeat password fields!";
        }
        String newEncryptedPassword = bCryptPasswordEncoder.encode(newPassword);
        return adminRepository.changePassword(user, newEncryptedPassword);

    }

}
