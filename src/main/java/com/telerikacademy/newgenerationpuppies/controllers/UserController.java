package com.telerikacademy.newgenerationpuppies.controllers;


import com.telerikacademy.newgenerationpuppies.DTO.TopTenDTO;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import com.telerikacademy.newgenerationpuppies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //DONE
    //The client must be able to see personal details of a subscriber
    @GetMapping("/info/{phoneNumber}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")
    public HashMap<String, String> getInfoSubscriber(@PathVariable int phoneNumber, HttpServletRequest httpServletRequest) {
        return userService.getSubscriberInfo(phoneNumber, httpServletRequest);
    }

    //DONE
    //A client should be able to see a history of the payments for its subscribers sorted descending by the date of payment
    @GetMapping("/payments")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")
    public List<Bill> getAllPayments(HttpServletRequest httpServletRequest){
        return userService.getAllPayments(httpServletRequest);
    }

    //A client should be able to see the average and MAX amount of money paid for a subscriber for a defined period of time
    //DONE
    @GetMapping("/reports/max/{phoneNumber}/{startDate}/{endDate}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")
    public Bill getMaxPaidFromSubscriber(@PathVariable int phoneNumber,
                                         @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @PathVariable ("endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                         HttpServletRequest httpServletRequest){
        return userService.getMaxPaidFromSubscriber(phoneNumber, startDate, endDate, httpServletRequest);
    }


    //A client should be able to see a list of the services the client( I think subscriber) has paid for
    //DONE
    @GetMapping("/services/{phoneNumber}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")
    public List<String> usedServicesFromSubscriber(@PathVariable int phoneNumber, HttpServletRequest httpServletRequest){
        return userService.usedServicesFromSubscriber(phoneNumber, httpServletRequest);
    }

    //Clients must have access to bill payment module where they can pay a particular bill (or selected list of bills)
    // for their subscribers
    //DONE
    @PostMapping("/pay/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")
    public String payBill(@PathVariable int id, HttpServletRequest httpServletRequest){
         return userService.payBill(id, httpServletRequest);
    }

    //Client can see report - Top 10 subscribers with the biggest amount of money paid.
    //DONE
    @GetMapping("/reports/10biggest-amounts")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")
    public List<TopTenDTO> getBiggestAmountsPaidBySubscribers(HttpServletRequest httpServletRequest){
        return userService.getBiggestAmountsPaidBySubscribers(httpServletRequest);
    }

    //A client should be able to see the AVERAGE and max amount of money payed for a subscriber for a defined period of time
    //DONE
    @GetMapping("/reports/average/{phoneNumber}/{startDate}/{endDate}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")
    public HashMap<String, Double> getAveragePaidFromSubscriber(@PathVariable int phoneNumber,
                                                                @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                @PathVariable ("endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                HttpServletRequest httpServletRequest){
        return userService.getAveragePaidFromSubscriber(phoneNumber, startDate, endDate, httpServletRequest);
    }

    @GetMapping("/unpaid/{phoneNumber}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_USER')")
    public List<Bill> getUnpaidBillsBySubscriber(@PathVariable int phoneNumber, HttpServletRequest httpServletRequest){
        return userService.getUnpaidBillsBySubscriber(phoneNumber, httpServletRequest);
    }

}
