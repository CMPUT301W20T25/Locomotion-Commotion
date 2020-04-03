package com.example.locomotioncommotion;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * User
 * All data associated with Users
 */
public class User {
    private String userName;
    private String passWord;
    private String phoneNumber;
    private String email;
    private Driver driver;
    private Rider rider;
    private ArrayList<String> notificationList;

    public User() {
        this.userName = "";
        this.passWord = "";
        this.phoneNumber = "";
        this.email = "";
        this.driver = null;
        this.rider = null;
        this.notificationList = new ArrayList<String>();
    }

    /**
     * Makes a new user.
     * @param userName
     *      The username of the user
     * @param passWord
     *      The password of the user
     */
    public User(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
        this.email = "";
        this.phoneNumber = "";
        this.driver = null;
        this.rider = null;
        this.notificationList = new ArrayList<String>();
    }

    /**
     * Updates the database entry corresponding to this user
     */
    public void updateDatabase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(this.getUserName()).set(this);
    }

    /**
     * Adds a pending notification to the list
     * @param notification
     *      A string representing the notification
     */
    public void addNotification(String notification){
        this.notificationList.add(notification);
    }

    /**
     * Pops the latest notification off of the user's notification list
     * @return
     *      Returns either the latest notification from the user's notification list, or "No notifications pending" if there are none
     */
    public String popNotification(){
        if(this.notificationList == null || this.notificationList.size() == 0){
            return "No notifications pending";
        } else {
            String notification = this.notificationList.get(this.notificationList.size()-1);
            this.notificationList.remove(this.notificationList.size()-1);
            return notification;
        }
    }

    /**
     * Gets the username of the user
     * @return
     *      The username of the user
     */
    public String getUserName(){
        return this.userName;
    }

    /**
     * Sets the username of the user
     * TODO: investigate whether this should even be a public method since username is so important
     * @param userName
     *      The new username to be set
     */
    public void setUserName(String userName){
        this.userName = userName;
    }

    /**
     * Gets the password of the user
     * TODO: investigate whether this is a massive security vulnerability
     * @return
     *      The password of the user
     */
    public String getPassWord(){
        return this.passWord;
    }

    /**
     * Sets the password of the user
     * TODO: investigate whether this is a massive security vulnerability
     * @param passWord
     *      The new password to be set
     */
    public void setPassWord(String passWord){
        this.passWord = passWord;
    }

    /**
     * Gets the phone number of the user
     * @return
     *      The phone number of the user
     */
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    /**
     * Sets the phone number of the iser
     * @param phoneNumber
     *      The new phone number to be set
     */
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the email address of the user
     * @return
     *      The user's email address
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Sets the email address of the user
     * @param email
     *      The new email address to be set
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Gets the rider object associated with the user
     * @return
     *      The rider object associated with the user
     */
    public Rider getRider(){
        return this.rider;
    }

    /**
     * Sets the rider object associated with the user
     * @param rider
     *      The new rider object to be linked with the user
     */
    public void setRider(Rider rider){
        this.rider = rider;
    }

    /**
     * Gets the driver object associated with the user
     * @return
     *      The driver object linked with the user
     */
    public Driver getDriver(){
        return this.driver;
    }

    /**
     * Sets the driver object associated with the user
     * @param driver
     *      The new driver object to be linked with the user
     */
    public void setDriver(Driver driver){
        this.driver = driver;
    }

    /**
     * If the user has a driver object associated with them, return the positive ratings of that object
     * Otherwise return -1
     * @return
     *      The positive ratings of the user-associated driver object, or -1 if there is no such object
     */
    public int getThumbsUp(){
        if(this.getDriver()== null){
            return -1;
        } else{
            return this.getDriver().getPositiveRatings();
        }
    }

    /**
     * If the user has a driver object associated with them, set the positive ratings of that object
     * //TODO: Make this throw an error if the user has no driver object associated with them
     * @param thumbs
     *      The new amount of positive ratings
     */
    public void setThumbsUp(int thumbs){
        if(this.getDriver()== null){
            //TODO: Have this throw an error or something
        }else{
            this.driver.setPositiveRatings(thumbs);
        }
    }
    /**
     * If the user has a driver object associated with them, return the negative ratings of that object
     * Otherwise return -1
     * @return
     *      The positive ratings of the user-associated driver object, or -1 if there is no such object
     */
    public int getThumbsDown(){
        if(this.getDriver() == null){
            return -1;
        } else{
            return this.driver.getNegativeRatings();
        }
    }
    /**
     * If the user has a driver object associated with them, set the negative ratings of that object
     * //TODO: Make this throw an error if the user has no driver object associated with them
     * @param thumbs
     *      The new amount of positive ratings
     */
    public void setThumbsDown(int thumbs){
        if(this.getDriver() == null){
            //TODO: Have this throw an error?
        } else{
            this.driver.setNegativeRatings(thumbs);
        }
    }
}
