package com.telerikacademy.newgenerationpuppies.repos.adminrepository;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminRepository {

    ResponseEntity saveUser(User user, String role, String repeatedPassword);

    ResponseEntity updateCredentialsForClient(String userName, User user);

    ResponseEntity changePassword(String newPassword, String name);

    ResponseEntity deleteUser(String userName);

    ResponseEntity issueBill(int ubscriber, Bill bill);

    ResponseEntity listAll(String role);





    User findUser(String nameOfAdmin);

    ResponseEntity listAllSubscribers();











}
