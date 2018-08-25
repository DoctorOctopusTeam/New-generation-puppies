package com.telerikacademy.newgenerationpuppies.repos;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface UserRepository {

    //    public List<User> returnUsers();
//
    public User findByUsername(String username);
//
//    public void saveUser(User user);
//
//    public User giveUserKtb10();
//
//    public Bill payBill(int id);
//
//    public User test();

    HashMap<String, String> getSubscriberInfo(int phoneNumber, HttpServletRequest httpServletRequest);

    List<Bill> getAllPayments(HttpServletRequest httpServletRequest);

    public Bill getMaxPaidFromSubscriber(int phoneNumber, HttpServletRequest httpServletRequest);

    public HashMap<String, Double> getAveragePaidFromSubscriber(int phoneNumber, HttpServletRequest httpServletRequest);

    List<Subscriber> getBiggestAmountsPaidBySubscribers(HttpServletRequest httpServletRequest);
}
