package com.example.locomotioncommotion;

//Singleton implementation based on
//https://stackoverflow.com/a/12587124

public class User {
    private String userName;
    private String passWord;
    private String phoneNumber;
    private String email;
    private Driver driver;
    private Rider rider;
    private static User userInstance;

    private User(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
        this.email = "";
        this.phoneNumber = "";
        this.driver = null;
        this.rider = null;
    }

    public synchronized static User getInstance(){
        if(User.userInstance == null){
            //TODO; make this throw an error
            return null;
        } else{
            return User.userInstance;
        }
    }

    public synchronized static User getInstance(String userName, String passWord){
        if(User.userInstance == null){
            User.userInstance = new User(userName, passWord);
            return User.userInstance;
        } else{
            return User.userInstance;
        }
    }

    public synchronized static User getInstance(String userName, String passWord, String email, String phonenumber, Driver driver, Rider rider){
        if(User.userInstance == null){
            User.userInstance = new User(userName, passWord);
            User.userInstance.email = email;
            User.userInstance.phoneNumber = phonenumber;
            User.userInstance.driver = driver;
            User.userInstance.rider = rider;
            return User.userInstance;
        } else{
            return User.userInstance;
        }
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    //TODO: investigate whether this is a massive security vulnerability
    public String getPassWord(){
        return this.passWord;
    }

    public void setPassWord(String passWord){
        this.passWord = passWord;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public Rider getRider(){
        return this.rider;
    }

    public void setRider(Rider rider){
        this.rider = rider;
    }

    public Driver getDriver(){
        return this.driver;
    }

    public void setDriver(Driver driver){
        this.driver = driver;
    }

}
