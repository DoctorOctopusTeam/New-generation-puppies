package com.telerikacademy.newgenerationpuppies.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.telerikacademy.newgenerationpuppies.adminservice.AdministartorService;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import com.telerikacademy.newgenerationpuppies.repos.adminrepository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    private AdministartorService administartorService;

    public AdminController(AdministartorService administartorService){
        this.administartorService = administartorService;
    }

    @PostMapping("/registeruser")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity registerUser(@RequestBody User user,
                                       @RequestParam String repeatedPassword,
                                       HttpServletRequest httpServletRequest
    ){
        if(!isAllowedIp(httpServletRequest))return returnForbidden();
        String role = "ROLE_USER";
        return administartorService.saveUser(user, role, repeatedPassword);
    }

    @PostMapping("/registeradmin")
    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    public ResponseEntity registerAdmin(@RequestBody User user,
                                        @RequestParam String repeatedPassword,
                                        HttpServletRequest httpServletRequest){
        if(!isAllowedIp(httpServletRequest))return returnForbidden();
        String role = "ROLE_UNAUTHORIZEDADMIN";
        return administartorService.saveUser(user, role, repeatedPassword);
    }

    @PostMapping("/updatecreds")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity updateClient(@RequestBody User user,
                                       @RequestParam String currentuserName,
                                       HttpServletRequest httpServletRequest){
        if(!isAllowedIp(httpServletRequest))return returnForbidden();
        return administartorService.updateClient(currentuserName, user, httpServletRequest);
    }
//---------------------------
    @PostMapping("/changepassword")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_UNAUTHORIZEDADMIN')")
    public ResponseEntity changePassword(@RequestParam String newPassword,
                                 @RequestParam String repeatNewPassword,
                                 HttpServletRequest httpServletRequest){
        if(!isAllowedIp(httpServletRequest))return returnForbidden();
        return administartorService.changePassword(newPassword, repeatNewPassword, httpServletRequest);
    }
//--------------------------------------------

    @PostMapping("/delete")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity deleteUser(@RequestParam String nameOfBank,
                                     HttpServletRequest httpServletRequest){
        if(!isAllowedIp(httpServletRequest))return returnForbidden();
        return administartorService.deleteUser(nameOfBank);
    }

    //--------
    @PostMapping("/issuebill")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity issueNewBill(@RequestParam int subscriber,
                                       @RequestBody Bill bill,
                                       HttpServletRequest httpServletRequest){
        if(!isAllowedIp(httpServletRequest))return returnForbidden();
        return administartorService.issueNewBill(subscriber, bill);
    }

    @GetMapping("/listall/{role}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity listAll(@PathVariable String role,
                                  HttpServletRequest httpServletRequest){
        if(!isAllowedIp(httpServletRequest))return returnForbidden();
        return administartorService.listAll(role);
    }

    @GetMapping("/subscribers")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity listAllSubscribers(HttpServletRequest httpServletRequest){
        if(!isAllowedIp(httpServletRequest))return returnForbidden();
        return administartorService.listAllSubscribers();
    }

    private boolean isAllowedIp(HttpServletRequest httpServletRequest){
        String ip = getIp(httpServletRequest);
        if(!ip.equals("0:0:0:0:0:0:0:1")){
            return false;
        }
        return true;
    }
    private String getIp(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = httpRequest.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = httpRequest.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = httpRequest.getHeader("HTTP_X_FORWARDED");
            }
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = httpRequest.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = httpRequest.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = httpRequest.getHeader("HTTP_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = httpRequest.getHeader("HTTP_FORWARDED");
            }
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = httpRequest.getHeader("HTTP_VIA");
            }
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = httpRequest.getHeader("REMOTE_ADDR");
            }
            if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
                ip = httpRequest.getRemoteAddr();
            }
            return ip;
        } else {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public ResponseEntity returnForbidden(){
        return ResponseEntity.status(403)
                .header("Access-Control-Expose-Headers","Error")
                .header("Error", "Only selected list of IPs are allowed")
                .body(null);
    }

}
