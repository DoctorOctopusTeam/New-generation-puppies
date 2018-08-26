package com.telerikacademy.newgenerationpuppies.service;

import com.telerikacademy.newgenerationpuppies.DTO.TopTenDTO;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
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

    public Bill getMaxPaidFromSubscriber(int phoneNumber, HttpServletRequest httpServletRequest);

    public HashMap<String, Double> getAveragePaidFromSubscriber(int phoneNumber, HttpServletRequest httpServletRequest);

    List<TopTenDTO> getBiggestAmountsPaidBySubscribers(HttpServletRequest httpServletRequest);

    Bill payBill(int id, HttpServletRequest httpServletRequest);
}
