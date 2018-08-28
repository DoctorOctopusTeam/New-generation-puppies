package com.telerikacademy.newgenerationpuppies.adminservice;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;

import java.util.List;

public interface AdministartorService {

    String registerUser(User user, String role);

    String registerAdmin(User user, String role);

    String changePassword(String newPassword, String nameOfBank);

    List<User> listAll(String role);

    String updateClient(String userName, User user);

    String deleteUser(String nameOfBank);

    Bill issueNewBill(int subscriber, Bill bill);
}
