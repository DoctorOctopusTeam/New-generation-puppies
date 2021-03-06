package com.telerikacademy.newgenerationpuppies.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private long EIK;

    @Column(name = "role")
    private String role;

    @Column(name = "email")
    private String email;


    @OneToMany(mappedBy = "user")
    //@JsonIgnoreProperties({"user", "bills"})
    @JsonIgnore
    private List<Subscriber> subscribers;

    @OneToOne
    @JoinColumn(name = "userName")
    private Authority authority;

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

    public long getEIK() {
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

    public void setEIK(long EIK) {
        this.EIK = EIK;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

