package com.telerikacademy.newgenerationpuppies.adminservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.adminrepository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Service
public class AdminServiceImpl implements AdministartorService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }



    @Override
    public ResponseEntity saveUser(User user, String role, String repeatedPassword) {
        if(adminRepository.findUser(user.getUserName()) != null){
            return returnResponseEntity("Username is already used!", user);
        }
        if(user.getUserName().equals("") || user.getPassword().equals("")){
            return returnResponseEntity("Username or/and password not entered!", user);
        }
        if(!repeatedPassword.equals(user.getPassword())){
            return returnResponseEntity("Both password fields must match!", user);
        }
        if(role.equals("ROLE_ADMIN") && user.getEmail().equals("")){
            return returnResponseEntity("Email is required!", user);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return adminRepository.saveUser(user, role, repeatedPassword);
    }

    @Override
    public ResponseEntity updateClient(String userName, User user, HttpServletRequest httpServletRequest) {
        if(userName.equals("")){
            return returnResponseEntity("No user specified!", user);
        }
        User currentStateOfUser = adminRepository.findUser(userName);
        if(currentStateOfUser == null){
            return returnResponseEntity("No such user in the database!", user);
        }
        String token = httpServletRequest.getHeader("Authorization");
        String nameOfAdmin = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();
        if(!nameOfAdmin.equals(userName) && currentStateOfUser.getAuthority().getAuthority().equals("ROLE_ADMIN")){
            return returnResponseEntity("Can not update other admin credentials!", currentStateOfUser);
        }
        if(!user.getPassword().equals("") ){
            if(!nameOfAdmin.equals(userName)){
                return returnResponseEntity("Can not update other passwords!", currentStateOfUser);
            }else{
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
        }

        return adminRepository.updateCredentialsForClient(userName, user);
    }


    @Override
    public ResponseEntity changePassword(String newPassword, String repeatPassword, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        String nameOfAdmin = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();
        User user = adminRepository.findUser(nameOfAdmin);
        if(!newPassword.equals(repeatPassword)){
            return returnResponseEntity("Both fields must have identical entries!", user);
        }
        String newEncryptedPassword = bCryptPasswordEncoder.encode(newPassword);
        return adminRepository.changePassword(newEncryptedPassword, nameOfAdmin);
    }

    @Override
    public List<User> listAll(String role) {
        return null;
    }


    @Override
    public String deleteUser(String nameOfBank) {
        return null;
    }

    @Override
    public Bill issueNewBill(int subscriber, Bill bill) {
        return null;
    }



    public ResponseEntity returnResponseEntity(String message, User user){
        return ResponseEntity.badRequest()
                .header("Access-Control-Expose-Headers","Error")
                .header("Error", message)
                .body(user);
    }
}
