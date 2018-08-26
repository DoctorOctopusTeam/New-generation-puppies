package com.telerikacademy.newgenerationpuppies.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bills")
public class  Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "service")
    private String service;

    @Column(name = "startDate")
    @Type(type="date")
    private Date startDate;

    @Column(name = "endDate")
    @Type(type="date")
    private Date endDate;

    @Column(name = "payDate")
    private Date payDate;

    @Column(name = "amount")
    private double amount;

    @Column(name = "currency")
    private String currency;

    @ManyToOne
    @JoinColumn(name = "phoneNumber")
    @JsonIgnoreProperties({"bills", "user"})
    private Subscriber subscriber;

    public Bill(){

    }

    public int getId() {
        return id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
}
