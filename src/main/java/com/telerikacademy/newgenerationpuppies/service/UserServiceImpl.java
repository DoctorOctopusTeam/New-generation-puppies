package com.telerikacademy.newgenerationpuppies.service;

import com.telerikacademy.newgenerationpuppies.DTO.TopTenDTO;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public List<User> returnUsers() {
//        return null;
//    }
//
//    @Override
//    public User findByUsername(String username) {
//        return null;
//    }
//
//    @Override
//    public void saveUser(User user) {
//
//    }
//
//    @Override
//    public User giveUserKtb10() {
//        return null;
//    }
//
//    @Override
//    public Bill payBill(int id) {
//        return null;
//    }
//
//    @Override
//    public User test() {
//        return null;
//    }

    @Override
    public Subscriber getSubscriberInfo(int phoneNumber, HttpServletRequest httpServletRequest) {
        return userRepository.getSubscriberInfo(phoneNumber, httpServletRequest);
    }

    @Override
    public List<Bill> getAllPayments(HttpServletRequest httpServletRequest) {
        return userRepository.getAllPayments(httpServletRequest);
    }

    @Override
    public ResponseEntity getMaxPaidFromSubscriber(int phoneNumber, LocalDate startDate, LocalDate endDate, HttpServletRequest httpServletRequest) {
        return userRepository.getMaxPaidFromSubscriber(phoneNumber, startDate, endDate, httpServletRequest);
    }

    @Override
    public ResponseEntity getAveragePaidFromSubscriber(int phoneNumber, LocalDate startDate, LocalDate endDate, HttpServletRequest httpServletRequest) {
        return userRepository.getAveragePaidFromSubscriber(phoneNumber, startDate, endDate, httpServletRequest);
    }

    @Override
    public List<TopTenDTO> getBiggestAmountsPaidBySubscribers(HttpServletRequest httpServletRequest) {
        return userRepository.getBiggestAmountsPaidBySubscribers(httpServletRequest);
    }

    @Override
    public Bill payBill(int id, HttpServletRequest httpServletRequest) {
         return userRepository.payBill(id, httpServletRequest);
    }

    @Override
    public List<String> usedServicesFromSubscriber(int phoneNumber, HttpServletRequest httpServletRequest) {
        return userRepository.usedServicesFromSubscriber(phoneNumber, httpServletRequest);
    }

    @Override
    public ResponseEntity getUnpaidBillsBySubscriber(int phoneNumber, HttpServletRequest httpServletRequest) {
        return userRepository.getUnpaidBillsBySubscriber(phoneNumber, httpServletRequest);
    }


    public ResponseEntity returnResponseEntity(String message, Object object){
        return ResponseEntity.badRequest()
                .header("Access-Control-Expose-Headers","Error")
                .header("Error", message)
                .body(object);
    }
}
