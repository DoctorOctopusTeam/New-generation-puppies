package com.telerikacademy.newgenerationpuppies.repos.adminrepository;

import com.telerikacademy.newgenerationpuppies.models.Authority;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<User> saveUser(User user, String role, String repeatedPassword) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        user.setEnabled(1);
        session.save(user);
        Authority authority = new Authority();
        authority.setAuthority(role);
        authority.setUserName(user.getUserName());
        session.save(authority);
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> updateCredentialsForClient(String userName, User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(!userName.equals(user.getUserName()) && !user.getUserName().equals("")){
            session.createQuery("update User u set u.userName= :x " +
                    "where u.userName= :y")
                    .setParameter("x", user.getUserName())
                    .setParameter("y", userName).executeUpdate();
            session.getTransaction().commit();
            session.beginTransaction();
        }
        String nameForSearch = user.getUserName().equals("")? userName : user.getUserName();
        User actualUser = session.get(User.class, nameForSearch);
        if(!user.getPassword().equals("")){
            actualUser.setPassword(user.getPassword());
        }
        if(!user.getDetails().equals("")){
            actualUser.setDetails(user.getDetails());
        }
        actualUser.setEIK(user.getEIK());
        if(!user.getEmail().equals("")){
            actualUser.setEmail(user.getEmail());
        }
        if(user.getEnabled() != 5){
            actualUser.setEnabled(user.getEnabled());
        }
        session.save(actualUser);
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>(actualUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity changePassword(String newPassword, String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, name);
        Authority authority = session.get(Authority.class, name);
        authority.setAuthority("ROLE_ADMIN");
        user.setPassword(newPassword);
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteUser(String userName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, userName);
//        Authority authority = session.get(Authority.class, userName);
//        session.delete(authority);
        session.delete(user);
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>(user, HttpStatus.OK);
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
    public ResponseEntity listAllSubscribers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Subscriber>list = session.createQuery("from Subscriber ").list();
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity issueBill(int subscriber, Bill bill) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        bill.setSubscriber(session.get(Subscriber.class, subscriber));
        session.save(bill);
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @Override
    public ResponseEntity listAll(String auth) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String role = "ROLE_" + auth.toUpperCase();
        List<User>list = session.createQuery("from User u where u.authority.authority= :x")
                .setParameter("x",role).list();
        session.getTransaction().commit();
        session.close();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }




}
