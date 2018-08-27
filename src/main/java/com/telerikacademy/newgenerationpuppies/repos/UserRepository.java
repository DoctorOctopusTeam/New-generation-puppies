package com.telerikacademy.newgenerationpuppies.repos;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;

import java.util.List;

public interface UserRepository {

    List<User> returnUsers();

    User findByUsername(String username);

    User giveUserKtb10();

    //Bill payBill(int id);
}
