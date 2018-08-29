package com.telerikacademy.newgenerationpuppies.repos;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.telerikacademy.newgenerationpuppies.DTO.TopTenDTO;
import com.telerikacademy.newgenerationpuppies.models.Authority;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(Subscriber.class)
            .addAnnotatedClass(Bill.class)
            .addAnnotatedClass(Authority.class)
            .buildSessionFactory();

    @Override
    public User findByUsername(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, username);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    //gets info about a particular subscriber - client of the logged in bank, based on his phone number passed in the URL
    //the parameter int phoneNumber is passed via PostMan as parameter, not as JS object
    //URL - localhost:8080/api/user/info/{phoneNumber}
    //DONE
    @Override
    public HashMap<String, String> getSubscriberInfo(int phoneNumber, HttpServletRequest httpServletRequest) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        HashMap<String, String> hash = new HashMap<>();

        String token = httpServletRequest.getHeader("Authorization");
        String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();

        Subscriber subscriber = session.get(Subscriber.class, phoneNumber);
        if (!subscriber.getUser().getUserName().equals(nameOfBank)) {
            session.getTransaction().commit();
            session.close();
            return null;
        } else {
            hash.put("First name", subscriber.getFirstName());
            hash.put("Last name", subscriber.getLastName());
            hash.put("EGN", String.valueOf(subscriber.getEgn()));
            hash.put("Phone number", String.valueOf(subscriber.getPhoneNumber()));
        }
        session.getTransaction().commit();
        session.close();
        return hash;
    }

    //gets top 10 paid bills from ALL subscribers of the logged in bank, based on the date ot payment in descending order
    // URL - localhost:8080/api/user/payments
    //DONE
    @Override
    public List<Bill> getAllPayments(HttpServletRequest httpServletRequest) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Bill> list = new ArrayList<>();

        String token = httpServletRequest.getHeader("Authorization");
        String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();

        list = session.createQuery("from Bill b where b.payDate != null AND " +
                "b.subscriber.user.userName =:nameOfBank order by payDate asc ")
                .setParameter("nameOfBank", nameOfBank)
                .setMaxResults(10).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    //gets the maximum sum paid from a subscriber for e defined period ot time
    //URL - localhost:8080/api/user/reports/max/{phoneNumber}
    //DONE
    @Override
    public Bill getMaxPaidFromSubscriber(int phoneNumber, LocalDate startDate, LocalDate endDate, HttpServletRequest httpServletRequest) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Bill> bills = new ArrayList<>();

        String token = httpServletRequest.getHeader("Authorization");
        String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();

        bills = session.createQuery("from Bill b where b.payDate != null AND " +
                ("b.subscriber.user.userName =:nameOfBank AND " +
                        "b.subscriber.phoneNumber =:phoneNumber AND " +
                        "b.payDate >:startDate AND b.payDate <:endDate order by b.amount desc " ))
                .setParameter("nameOfBank", nameOfBank)
                .setParameter("phoneNumber", phoneNumber)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate).list();
        session.getTransaction().commit();
        session.close();
        if (bills.size() == 0) {
            return null;
        } else {

            return bills.get(0);
        }
    }


    //gets the top ten subscribers based on the paid sums for services
    //URL - localhost:8080/api/user/reports/10biggest-amounts
    //DONE
    @Override
    public HashMap<String, TopTenDTO> getBiggestAmountsPaidBySubscribers(HttpServletRequest httpServletRequest) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        HashMap<String, TopTenDTO> hash = new HashMap<>();
        List<TopTenDTO> topTenDTO = new ArrayList<>();
        String token = httpServletRequest.getHeader("Authorization");
        String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();

//        topTenDTO = session.createQuery("select s.firstName, s.lastName, b.subscriber.phoneNumber, sum(b.amount) from Subscriber s" +
//                " inner join Bill b on s.phoneNumber=b.subscriber.phoneNumber where" +
//                " b.payDate != null AND b.subscriber.user.userName =:nameOfBank" +
//                " group by b.subscriber.phoneNumber, s.firstName, s.lastName" +
//                " order by sum(b.amount) desc ").setParameter("nameOfBank", nameOfBank).setMaxResults(10).list();


        topTenDTO = session.createQuery("select s.firstName, s.lastName, b.subscriber.phoneNumber, sum((b.amount) * " +
                "CASE b.currency when 'USD' THEN 1.5 WHEN 'EUR' THEN 2.0 ELSE 1.0 END) from Subscriber s" +
                " inner join Bill b on s.phoneNumber=b.subscriber.phoneNumber where" +
                " b.payDate != null AND b.subscriber.user.userName =:nameOfBank" +
                " group by b.subscriber.phoneNumber, s.firstName, s.lastName" +
                " order by sum(b.amount) desc ").setParameter("nameOfBank", nameOfBank).setMaxResults(10).list();

        for (int i = 0; i < topTenDTO.size(); i++) {
            hash.put(" Number " + (i+1), topTenDTO.get(i) );
        }

        session.getTransaction().commit();
        session.close();

        return hash;
    }

    //user pays a particular subscriber's bill chosen by the bill's id
    //URL - localhost:8080/api/user/pay/{id}
    //DONE
    @Override
    public Bill payBill(int id, HttpServletRequest httpServletRequest) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Bill bill = new Bill();
        String token = httpServletRequest.getHeader("Authorization");
        String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();
        Query query = session.createQuery("from Bill b where b.id=:id AND b.subscriber.user.userName =:nameOfBank")
                .setParameter("id", id)
                .setParameter("nameOfBank", nameOfBank);

        bill = (Bill)query.getSingleResult();

        bill.setPayDate(LocalDate.now());
        session.update(bill);
        session.getTransaction().commit();
        session.close();
        return bill;
    }

    //gets info about all the services particular subscriber uses
    //URL - localhost:8080/api/user/services/{phoneNumber}
    //DONE
    @Override
    public List<String> usedServicesFromSubscriber(int phoneNumber, HttpServletRequest httpServletRequest) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<String> list = new ArrayList<>();
        String token = httpServletRequest.getHeader("Authorization");
        String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();
        list = session.createQuery("select b.service from Bill b where b.subscriber.user.userName =:nameOfBank " +
                "AND b.subscriber.phoneNumber =:phoneNumber order by payDate desc ")
                .setParameter("nameOfBank", nameOfBank)
                .setParameter("phoneNumber", phoneNumber).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    //gets the average sum paid from a customer for a defined period ot time
    //URL - localhost:8080/api/user/reports/average/{phoneNumber}
    //DONE
    @Override
    public HashMap<String, Double> getAveragePaidFromSubscriber(int phoneNumber, LocalDate startDate, LocalDate endDate, HttpServletRequest httpServletRequest) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Double averageSum = 0d;
        HashMap<String, Double> hash = new HashMap<>();

        String token = httpServletRequest.getHeader("Authorization");
        String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();

        Query query = session.createQuery("select round(avg(b.amount), 2) from Bill b where b.payDate != null AND " +
                ("b.subscriber.user.userName =:nameOfBank AND " +
                        "b.subscriber.phoneNumber =:phoneNumber AND " +
                        "b.payDate >:startDate AND b.payDate <:endDate"))
                .setParameter("nameOfBank", nameOfBank)
                .setParameter("phoneNumber", phoneNumber)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate);



        averageSum = (Double) query.getSingleResult();
        session.getTransaction().commit();
        session.close();
        hash.put("Average sum", averageSum);
        return hash;
    }

}
