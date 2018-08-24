package com.telerikacademy.newgenerationpuppies.repos;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telerikacademy.newgenerationpuppies.dtomodels.DTOUser;
import com.telerikacademy.newgenerationpuppies.models.Authority;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

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
//---------------------
    public Object listUser(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //List<User> list = session.createQuery("from User u where u.userName='Ktb-10' ").list();
        //List<Subscriber> list = session.createQuery("from Subscriber s where s.phoneNumber = 878002 ").list();
        //List<Bill> list = session.createQuery("from Bill b where b.id=6 ").list();
        //List<Authority> list = session.createQuery("from Authority a where a.userName='FIB' ").list();

        //List<User> list = session.createQuery("from User u ").list();
        //List<Subscriber> list = session.createQuery("from Subscriber ").list();
        //List<Bill> list = session.createQuery("from Bill ").list();
        List<Authority> list = session.createQuery("from Authority ").list();
        session.getTransaction().commit();
        session.close();
        return list;//.get(0);
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

    //@Override
    public void saveUser(User user, String authorityRole){

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

    public String test(HttpServletRequest httpServletRequest) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String str = httpServletRequest.getUserPrincipal().getName();
        String token = httpServletRequest.getHeader("Authorization");
        String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();
        //List<Authority> authority = session.createQuery("from Authority where user='IvanBank'").list();
        //User user = session.get(User.class, "Ktb-10");
        session.getTransaction().commit();
        session.close();
        return nameOfBank + httpServletRequest.isUserInRole("ROLE_ADMIN");
    }

    public List<Map<String, String>> history(HttpServletRequest request){
        List<Map<String, String>> list = new ArrayList<>();
        String token = request.getHeader("Authorization");
        String nameOfBank = JWT.require(Algorithm.HMAC512("SecretKeyToGenJWTs".getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Bill> payedBills =
                session.createQuery("from Bill b " +
                        "where b.payDate != null " +
                        "and b.subscriber.user.userName = :x ")
                        .setParameter("x", nameOfBank).list();
        for (int i = 0; i < payedBills.size(); i++){
            list.add(new HashMap<String, String>());
            list.get(i).put("valutata", payedBills.get(i).getCurrency());
            list.get(i).put("uslugata", payedBills.get(i).getService());
            list.get(i).put("dali e platena maika mu da eba", payedBills.get(i).getPayDate().toString());
            list.get(i).put("suma", String.valueOf(payedBills.get(i).getAmount()));
        }
        return list;
    }



}
