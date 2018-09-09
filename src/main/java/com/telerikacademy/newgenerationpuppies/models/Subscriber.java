package com.telerikacademy.newgenerationpuppies.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscribers")
//@JsonIgnoreProperties({"user", "bills"})
public class Subscriber {
    @Id
    @Column(name = "phoneNumber")
    private int phoneNumber;

    @Column(name = "firstName")
    private  String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "EGN")
    private long egn;

    @ManyToOne
    @JoinColumn(name = "userName")
    @JsonIgnoreProperties({"subscribers"})
    private User user;

    @OneToMany(mappedBy = "subscriber")
    @JsonIgnoreProperties("subscriber")
    private List<Bill> bills;

    public Subscriber(){

    }

    public Subscriber(int phoneNumber, String firstName, String lastName, long egn, User user, List<Bill>bills) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.egn = egn;
        this.user = user;
        this.bills = bills;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public long getEgn() {
        return egn;
    }

    public void setEgn(long egn) {
        this.egn = egn;
    }

    public User getUser() {
        return user;
    }

    public List<Bill> getBills(){
        return bills;
    }
}

