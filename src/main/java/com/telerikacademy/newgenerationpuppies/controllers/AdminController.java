package com.telerikacademy.newgenerationpuppies.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.telerikacademy.newgenerationpuppies.adminservice.AdministartorService;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import com.telerikacademy.newgenerationpuppies.repos.adminrepository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private AdminRepository adminRepository;
    private AdministartorService administartorService;
    public AdminController(AdministartorService administartorService){
        this.administartorService = administartorService;
        this.adminRepository = adminRepository;
    }

    @PostMapping("/registeruser")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity registerUser(@RequestBody User user, @RequestParam String repeatedPassword){
        String role = "ROLE_USER";
        return administartorService.saveUser(user, role, repeatedPassword);
    }

    @PostMapping("/registeradmin")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity registerAdmin(@RequestBody User user, @RequestParam String repeatedPassword){
        String role = "ROLE_UNAUTHORIZEDADMIN";
        return administartorService.saveUser(user, role, repeatedPassword);
    }

    @PostMapping("/updatecreds")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity updateClient(@RequestBody User user, @RequestParam String currentuserName,
                                       HttpServletRequest httpServletRequest){
        return administartorService.updateClient(currentuserName, user, httpServletRequest);
    }
//---------------------------
    @PutMapping("/changepassword")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_UNAUTHORIZEDADMIN')")
    public ResponseEntity changePassword(@RequestParam String newPassword,
                                 @RequestParam String repeatNewPassword,
                                 HttpServletRequest httpServletRequest){
        return administartorService.changePassword(newPassword, repeatNewPassword, httpServletRequest);
    }
//--------------------------------------------
    @GetMapping("/listall/{role}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public List<User> listAll(@PathVariable String role){
        return adminRepository.listAll(role);
    }


    @DeleteMapping("/delete/{nameofbank}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public String deleteUser(@PathVariable String nameofbank){
        return adminRepository.deleteUser(nameofbank);
    }

    @PostMapping("/issuebill")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public Bill issueNewBill(@RequestParam int subscriber, @RequestBody Bill bill){

        return adminRepository.issueBill(subscriber, bill);
    }

}
