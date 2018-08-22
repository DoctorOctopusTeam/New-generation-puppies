package com.telerikacademy.newgenerationpuppies.models;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @Column(name = "username")
    private String userName;

    @Column(name = "authority")
    private String authority;

    @OneToOne(mappedBy = "authority")
    private User user;

    public Authority(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
