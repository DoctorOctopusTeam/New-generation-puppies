package com.telerikacademy.newgenerationpuppies.repos;

import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Repository
public class UserRepository {

    private SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(Subscriber.class)
            .addAnnotatedClass(Bill.class)
            .buildSessionFactory();


    public List<User> returnUsers(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<User> list = session.createQuery("from User ").list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    public User findByUsername(String username){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, username);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public void saveUser(User user){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

}
