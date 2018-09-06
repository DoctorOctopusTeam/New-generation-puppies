package com.telerikacademy.newgenerationpuppies.adminservice;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdministartorService {

    ResponseEntity saveUser(User user, String role, String repeatedPassword);

    ResponseEntity updateClient(String userName, User user, HttpServletRequest httpServletRequest);


    ResponseEntity changePassword(String newPassword, String nameOfBank, HttpServletRequest httpServletRequest);

    ResponseEntity deleteUser(String nameOfBank);

    ResponseEntity issueNewBill(int subscriber, Bill bill);

    List<User> listAll(String role);

    ResponseEntity listAllSubscribers();




}
