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
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
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
        User user = new User();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            user = session.get(User.class, username);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    //gets info about a particular subscriber
    //URL - localhost:8080/user/info/{phoneNumber}
    @Override
    public HashMap<String, String> getSubscriberInfo(int phoneNumber, HttpServletRequest httpServletRequest) {
        HashMap<String, String> hash = new HashMap<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hash;
    }

    //gets top 10 paid bills from ALL subscribers of the logged in bank, based on the date ot payment in descending order
    // URL - localhost:8080/user/payments
    @Override
    public List<Bill> getAllPayments(HttpServletRequest httpServletRequest) {
        List<Bill> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    //gets the maximum sum paid from a subscriber for e defined period ot time
    //URL - localhost:8080/user/reports/max/{phoneNumber}
    @Override
    public Bill getMaxPaidFromSubscriber(int phoneNumber, LocalDate startDate, LocalDate endDate, HttpServletRequest httpServletRequest) {
        List<Bill> bills = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String token = httpServletRequest.getHeader("Authorization");
            String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                    .build()
                    .verify(token.replace("Bearer ", ""))
                    .getSubject();

            bills = session.createQuery("from Bill b where b.payDate != null AND " +
                    ("b.subscriber.user.userName =:nameOfBank AND " +
                            "b.subscriber.phoneNumber =:phoneNumber AND " +
                            "b.payDate >:startDate AND b.payDate <:endDate order by b.amount desc "))
                    .setParameter("nameOfBank", nameOfBank)
                    .setParameter("phoneNumber", phoneNumber)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate).list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (bills.size() == 0) {
            return null;
        } else {

            return bills.get(0);
        }
    }


    //gets the top ten subscribers based on the paid sums for services
    //URL - localhost:8080/user/reports/10biggest-amounts
    @Override
    public List<TopTenDTO> getBiggestAmountsPaidBySubscribers(HttpServletRequest httpServletRequest) {
        List<TopTenDTO> topTenDTO = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String token = httpServletRequest.getHeader("Authorization");
            String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                    .build()
                    .verify(token.replace("Bearer ", ""))
                    .getSubject();

            topTenDTO = session.createQuery("select s.firstName as firstName, s.lastName as lastName, " +
                    "b.subscriber.phoneNumber as phoneNumber, sum((b.amount)  * " +
                    "CASE b.currency when 'USD' THEN 1.5 WHEN 'EUR' THEN 2.0 ELSE 1.0 END) as amount from Subscriber s" +
                    " inner join Bill b on s.phoneNumber=b.subscriber.phoneNumber where" +
                    " b.payDate != null AND b.subscriber.user.userName =:nameOfBank" +
                    " group by b.subscriber" +
                    " order by amount desc ")
                    .setParameter("nameOfBank", nameOfBank)
                    .setMaxResults(10)
                    .setResultTransformer(Transformers.aliasToBean(TopTenDTO.class)).list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return topTenDTO;
    }

    //user pays a particular subscriber's bill chosen by the bill's id
    //URL - localhost:8080/user/pay/{id}
    @Override
    public String payBill(int id, HttpServletRequest httpServletRequest) {
        Bill bill = new Bill();
        String result = "";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String token = httpServletRequest.getHeader("Authorization");
            String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                    .build()
                    .verify(token.replace("Bearer ", ""))
                    .getSubject();
            Query query = session.createQuery("from Bill b where b.id=:id AND b.subscriber.user.userName =:nameOfBank")
                    .setParameter("id", id)
                    .setParameter("nameOfBank", nameOfBank);

            bill = (Bill) query.getSingleResult();
            if(bill.getPayDate() == null){
                bill.setPayDate(LocalDate.now());
                session.update(bill);
                session.getTransaction().commit();
                session.close();
                result = "Paid";
            } else{
                session.getTransaction().commit();
                session.close();
                return "Already paid";
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //gets info about all the services particular subscriber uses
    //URL - localhost:8080/user/services/{phoneNumber}
    @Override
    public List<String> usedServicesFromSubscriber(int phoneNumber, HttpServletRequest httpServletRequest) {
        List<String> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


    //gets the average sum paid from a customer for a defined period ot time
    //URL - localhost:8080/user/reports/average/{phoneNumber}/{startDate}/{endDate}
    @Override
    public HashMap<String, Double> getAveragePaidFromSubscriber(int phoneNumber, LocalDate startDate, LocalDate endDate, HttpServletRequest httpServletRequest)  {
        Double averageSum = 0d;
        HashMap<String, Double> hash = new HashMap<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(averageSum != 0){
            hash.put("Average sum", averageSum);
        } else {
            hash.put("Average sum", 0D);
        }

        return hash;
    }

    //gets list of all unpaid bills of particular subscriber
    //URL - localhost:8080/user/unpaid/{phoneNumber}
    @Override
    public List<Bill> getUnpaidBillsBySubscriber(int phoneNumber, HttpServletRequest httpServletRequest) {
        List<Bill> list = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String token = httpServletRequest.getHeader("Authorization");
            String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                    .build()
                    .verify(token.replace("Bearer ", ""))
                    .getSubject();
            list = session.createQuery("from Bill b where b.subscriber.user.userName =:nameOfBank " +
                    "AND b.subscriber.phoneNumber =:phoneNumber AND b.payDate=NULL order by b.endDate desc ")
                    .setParameter("nameOfBank", nameOfBank)
                    .setParameter("phoneNumber", phoneNumber).list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
