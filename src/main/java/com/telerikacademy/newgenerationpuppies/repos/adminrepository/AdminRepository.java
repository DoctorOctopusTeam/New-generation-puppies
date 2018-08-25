package com.telerikacademy.newgenerationpuppies.repos.adminrepository;

import com.telerikacademy.newgenerationpuppies.models.User;

public interface AdminRepository {

    String saveUser(User user, String role);

    String changePassword(String newPassword, String name);

    User findUser(String nameOfAdmin);





}
