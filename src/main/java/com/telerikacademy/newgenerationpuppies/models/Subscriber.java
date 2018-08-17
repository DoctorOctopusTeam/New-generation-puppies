package com.telerikacademy.newgenerationpuppies.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @Column(name = "phoneNumber")
    private int phoneNumber;

    @Column(name = "firstName")
    private  String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "EGN")
    private int egn;


    @ManyToOne
    @JoinColumn(name = "userName")
    private User user;

    @OneToMany(mappedBy = "subscriber")
    private List<Bill> bills;

    public Subscriber(){

    }

    public Subscriber(int phoneNumber, String firstName, String lastName, int egn, User user) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.egn = egn;
        this.user = user;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getEgn() {
        return egn;
    }

    public User getUser() {
        return user;
    }

    public List<Bill> getBills(){
        return bills;
    }
}

