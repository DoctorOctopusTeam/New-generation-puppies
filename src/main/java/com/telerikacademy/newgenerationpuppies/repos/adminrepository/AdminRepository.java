package com.telerikacademy.newgenerationpuppies.repos.adminrepository;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AdminRepository {

    ResponseEntity saveUser(User user, String role, String repeatedPassword);

    String changePassword(String newPassword, String name);

    User findUser(String nameOfAdmin);

    List<User> listAll(String role);

    ResponseEntity updateCredentialsForClient(String userName, User user);

    String deleteUser(String userName);

    Bill issueBill(int ubscriber, Bill bill);



}
