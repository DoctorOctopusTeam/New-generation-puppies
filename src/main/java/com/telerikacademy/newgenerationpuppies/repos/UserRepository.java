package com.telerikacademy.newgenerationpuppies.repos;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;

import java.util.List;

public interface UserRepository {

    public List<User> returnUsers();

    public User findByUsername(String username);

    public void saveUser(User user);

    public User giveUserKtb10();

    public Bill payBill(int id);
}
