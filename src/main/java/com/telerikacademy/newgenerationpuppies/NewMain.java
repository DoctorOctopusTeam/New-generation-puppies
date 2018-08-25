package com.telerikacademy.newgenerationpuppies;

import com.telerikacademy.newgenerationpuppies.models.Authority;
import com.telerikacademy.newgenerationpuppies.models.Bill;
import com.telerikacademy.newgenerationpuppies.models.Subscriber;
import com.telerikacademy.newgenerationpuppies.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewMain {
    public static  void main(String[] args){

        SessionFactory fak = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Subscriber.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Bill.class)
                .addAnnotatedClass(Authority.class)
                .buildSessionFactory();
        Session ses = fak.openSession();
        ses.beginTransaction();
        //POPULATE WITH USERS
        //----------------------------------
//        for (int i = 1; i <= 20; i++){
//            User u = new User();
//            u.setUserName("Ktb-" + i);
//            u.setPassword("0000@" + i);
//            u.setDetails("ala bala " + i);
//            u.setEIK(1000 + i);
//            u.setRole("CLIENT");
//            ses.save(u);
//            System.out.println("Client " + u.toString() + "successfully added");
//        }
        //POPULATE SUBSCRIBRES
        //------------------------------------------------
//        User user = ses.get(User.class, "Ktb-1");
////
//        for (int i = 1; i <=100; i++){
//            Subscriber subscriber = new Subscriber(
//                    898000 + i,
//                    "Delian" + i,
//                    "Peevski" + i,
//                    900000 + i,
//                    user);
//            ses.save(subscriber);
//        }
        //POPULATE BILLS
        //--------------------------------------

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Subscriber subscriber =ses.get(Subscriber.class,898001);
//
//        for (int i = 1; i <= 5; i++){
//            Bill bill = new Bill();
//            bill.setService("Internet + " + i);
//            bill.setStartDate(new Date());
//            bill.setEndDate(new Date());
//            bill.setAmount(100D + i + 200);
//            bill.setCurrency("BGN");
//            bill.setSubscriber(subscriber);
//            ses.save(bill);
//
//        }

//        Bill bill = ses.get(Bill.class, 3);
//        Subscriber subscriber = ses.get(Subscriber.class, 878002);
//        User user = ses.get(User.class, "Ktb-10");
//
//        System.out.println();
//        System.out.println();
//        System.out.println("This bill is to be paid at bank " + bill.getSubscriber().getUser().getUserName());
//        System.out.println("This subscriber's bill N3 has servise " + subscriber.getBills().get(3).getService());
//        System.out.println("This user's subscriber N10 is " + user.getSubscribers().get(10).getFirstName());

        User testUser1 = ses.get(User.class, "Ktb-10");
        //Authority authority = ses.get(Authority.class, "IvanBank");
        User testUser2 = ses.get(User.class, "Ktb-1");

        List<Subscriber>list = ses.createQuery("from Subscriber where user='Ktb-10'").list();
        List<Subscriber>list1 = ses.createQuery("from Subscriber where user='Ktb-1'").list();

        for (int i = 0; i < 30; i++){
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < testUser2.getSubscribers().size(); i++){
            System.out.println(testUser1.getSubscribers().get(i).getPhoneNumber());
        }
        User user = ses.get(User.class,"Ktb-20");
        user.setPassword("novaparola");

//        System.out.println(ses.get(Bill.class, 3).getPayDate().toString());
//        System.out.println(testUser1.getAuthority().getAuthority() + "----------------");
//        System.out.println(authority.getUser().getUserName()+ "---------------------");
        ses.getTransaction().commit();
        ses.close();

    }
}

