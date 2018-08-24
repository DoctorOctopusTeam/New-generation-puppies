package com.telerikacademy.newgenerationpuppies.service;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Service
public interface UserService {
//    public List<User> returnUsers();
//
//    public User findByUsername(String username);
//
//    public void saveUser(User user);
//
//    public User giveUserKtb10();
//
//    public Bill payBill(int id);
//
//    public User test();

    public HashMap<String, String> getSubscriberInfo(int phoneNumber, HttpServletRequest httpServletRequest);

    public List<Bill> getAllPayments(HttpServletRequest httpServletRequest);

    public Bill getMaxPayedFromSubscriber(int phoneNumber, HttpServletRequest httpServletRequest);

    public HashMap<String, Double> getAveragePayedFromSubscriber(int phoneNumber, HttpServletRequest httpServletRequest);

    List<Subscriber> getBiggestAmountsPayedBySubscribers(HttpServletRequest httpServletRequest);
}
