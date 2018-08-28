package com.telerikacademy.newgenerationpuppies.adminservice;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.User;

import java.util.List;

public class AdminServiceImpl implements AdministartorService {
    @Override
    public String registerUser(User user, String role) {
        return null;
    }

    @Override
    public String registerAdmin(User user, String role) {
        return null;
    }

    @Override
    public String changePassword(String newPassword, String nameOfBank) {
        return null;
    }

    @Override
    public List<User> listAll(String role) {
        return null;
    }

    @Override
    public String updateClient(String userName, User user) {
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
}
