package com.telerikacademy.newgenerationpuppies.repos.adminrepository;

import com.telerikacademy.newgenerationpuppies.models.Authority;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

    private SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(Subscriber.class)
            .addAnnotatedClass(Bill.class)
            .addAnnotatedClass(Authority.class)
            .buildSessionFactory();

    @Override
    public String saveUser(User user, String role) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        Authority authority = new Authority();
        authority.setAuthority(role);
        authority.setUserName(user.getUserName());
        session.save(authority);
        session.getTransaction().commit();
        session.close();
        return "User " + user.getUserName() + " with authority "
                + authority.getAuthority() + " created!";
    }

    @Override
    public String changePassword(String newPassword, String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, name);
        user.setPassword(newPassword);
        session.getTransaction().commit();
        session.close();
        return "Password has been change!";
    }

    @Override
    public User findUser(String nameOfAdmin) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        System.out.println();
        User user = session.get(User.class, nameOfAdmin);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public List<User> listAll(String auth) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String role = "ROLE_" + auth.toUpperCase();
        List<User>list = session.createQuery("from User u " +
                "where u.authority.authority= :x")
                .setParameter("x",role).list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    
}
