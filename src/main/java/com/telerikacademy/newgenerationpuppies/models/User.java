package com.telerikacademy.newgenerationpuppies.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {


    @Id
    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private  String password;

    @Column(name = "enabled")
    private int enabled;

    @Column(name = "details")
    private String details;

    @Column(name = "EIK")
    private int EIK;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Subscriber> subscribers;

    public User(){

    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDetails() {
        return details;
    }

    public int getEIK() {
        return EIK;
    }

    public String getRole() {
        return role;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setEIK(int EIK) {
        this.EIK = EIK;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}

