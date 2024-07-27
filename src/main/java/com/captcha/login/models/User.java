package com.captcha.login.models;

import jakarta.persistence.*;

@Entity
@Table(name = "login")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;


    private String email;
    private String address;
    private String number;
    private String password;



    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public User(){

    }

    public User( String name, String email, String address, String number, String password) {

        this.name = name;
        this.email = email;
        this.address = address;
        this.number = number;
        this.password = password;
    }
}
