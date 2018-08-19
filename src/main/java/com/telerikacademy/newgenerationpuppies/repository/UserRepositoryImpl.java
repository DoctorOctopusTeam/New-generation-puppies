package com.telerikacademy.newgenerationpuppies.repository;

import com.telerikacademy.newgenerationpuppies.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public Iterable<User> save(Iterable<? extends User> entities) {
        return null;
    }

    @Override
    public User findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void delete(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
