package com.telerikacademy.newgenerationpuppies.repos.adminrepository;

import com.telerikacademy.newgenerationpuppies.models.User;

public interface AdminRepository {

    String saveUser(User user, String role);

    String changePassword(User user, String newPassword);

    User findUser(String nameOfAdmin);





}
