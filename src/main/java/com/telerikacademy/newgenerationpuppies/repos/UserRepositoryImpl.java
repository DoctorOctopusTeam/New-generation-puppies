package com.telerikacademy.newgenerationpuppies.repos;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.telerikacademy.newgenerationpuppies.models.Authority;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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
    public List<User> returnUsers(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> list = session.createQuery("from User ").list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public User findByUsername(String username){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, username);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public void saveUser(User user){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);

        Authority authority = new Authority();
        authority.setAuthority(user.getRole());
        authority.setUserName(user.getUserName());
        session.save(authority);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User giveUserKtb10(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User u = session.get(User.class, "IvanBank");
        session.getTransaction().commit();
        session.close();
        return u;
    }

    @Override
    public Bill payBill(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Bill bill = session.get(Bill.class, id);
        //bill.setPayDate(null);
        bill.setPayDate(new Date());
        session.update(bill);
        session.getTransaction().commit();
        session.close();
        return bill;
    }
    @Override
    public User test(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //List<Authority> authority = session.createQuery("from Authority where user='IvanBank'").list();
        User user = session.get(User.class, "Ktb-10");
        session.getTransaction().commit();
        session.close();
        return user;
    }

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
        if(!subscriber.getUser().getUserName().equals(nameOfBank)){
            System.out.println(nameOfBank);
            System.out.println(subscriber.getUser().getUserName());
            session.getTransaction().commit();
            session.close();
            return null;
        } else{
            hash.put("username", subscriber.getFirstName());
        }
        session.getTransaction().commit();
        session.close();
        return hash;
    }


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

        list = session.createQuery("from Bill b where b.payDate != null ").setMaxResults(10).list();
        return list;
    }
}
