package com.telerikacademy.newgenerationpuppies.DTO;

import java.util.List;

public class TopTenDTO {
    private String firstName;

    private String lastName;

    private double amount;

    private String currency;

    private int phoneNumber;

    public TopTenDTO(String firstName, String lastName, double amount, int phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount = amount;
        this.phoneNumber = phoneNumber;
    }

    public TopTenDTO(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
