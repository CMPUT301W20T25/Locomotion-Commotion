package com.example.locomotioncommotion;

public class User {
    private String userName;
    private String passWord;
    private String phoneNumber;
    private String email;
    private Driver driver;
    private Rider rider;

    public User(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
        this.email = "";
        this.phoneNumber = "";
        this.driver = null;
        this.rider = null;
    }

}
