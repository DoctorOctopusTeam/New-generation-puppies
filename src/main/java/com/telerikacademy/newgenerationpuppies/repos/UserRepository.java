package com.telerikacademy.newgenerationpuppies.repos;

import com.telerikacademy.newgenerationpuppies.DTO.TopTenDTO;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
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

    Subscriber getSubscriberInfo(int phoneNumber, HttpServletRequest httpServletRequest);

    List<Bill> getAllPayments(HttpServletRequest httpServletRequest);

    public ResponseEntity getMaxPaidFromSubscriber(int phoneNumber, LocalDate startDate, LocalDate endDate, HttpServletRequest httpServletRequest);

    public ResponseEntity getAveragePaidFromSubscriber(int phoneNumber, LocalDate startDate, LocalDate endDate, HttpServletRequest httpServletRequest);

    List<TopTenDTO> getBiggestAmountsPaidBySubscribers(HttpServletRequest httpServletRequest);

    Bill payBill(int id, HttpServletRequest httpServletRequest);

    List<String> usedServicesFromSubscriber(int phoneNumber, HttpServletRequest httpServletRequest);

    ResponseEntity getUnpaidBillsBySubscriber(int phoneNumber, HttpServletRequest httpServletRequest);
}
