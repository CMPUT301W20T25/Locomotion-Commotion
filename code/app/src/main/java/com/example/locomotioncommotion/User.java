package com.example.locomotioncommotion;

//Singleton implementation based on
//https://stackoverflow.com/a/12587124

/**
 * User
 * All data associated with Users
 * implements a singleton so it can be accessed anywhere
 */
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
            User.userInstance.setEmail(email);
            User.userInstance.setPhoneNumber(phonenumber);
            User.userInstance.setDriver(driver);
            User.userInstance.setRider(rider);
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

    public int getThumbsUp(){
        if(this.getDriver()== null){
            return -1;
        } else{
            return this.getDriver().getPositiveRatings();
        }
    }

    public void setThumbsUp(int thumbs){
        if(this.getDriver()== null){
            //TODO: Have this throw an error or something
        }else{
            this.driver.setPositiveRatings(thumbs);
        }
    }

    public int getThumbsDown(){
        if(this.getDriver() == null){
            return -1;
        } else{
            return this.driver.getNegativeRatings();
        }
    }

    public void setThumbsDown(int thumbs){
        if(this.getDriver() == null){
            //TODO: Have this throw an error?
        } else{
            this.driver.setNegativeRatings(thumbs);
        }
    }
}
