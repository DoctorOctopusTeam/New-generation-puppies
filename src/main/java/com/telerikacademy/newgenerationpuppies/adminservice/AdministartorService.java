package com.telerikacademy.newgenerationpuppies.adminservice;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdministartorService {

    ResponseEntity saveUser(User user, String role, String repeatedPassword);

    String changePassword(String newPassword, String nameOfBank);

    List<User> listAll(String role);

    ResponseEntity updateClient(String userName, User user);

    String deleteUser(String nameOfBank);

    Bill issueNewBill(int subscriber, Bill bill);
}
