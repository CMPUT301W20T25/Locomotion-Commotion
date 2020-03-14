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

    /**
     * Makes a new user. Private, as this is a singleton class.
     * @param userName
     *      The username of the user
     * @param passWord
     *      The password of the user
     */
    private User(String userName, String passWord){
        this.userName = userName;
        this.passWord = passWord;
        this.email = "";
        this.phoneNumber = "";
        this.driver = null;
        this.rider = null;
    }

    /**
     * Gets an instance of the logged-in user. If there isn't one then return null, since a user with no username makes no sense
     * @return
     *      Returns the logged-in user object, or null if there is no logged-in user
     */
    public synchronized static User getInstance(){
        if(User.userInstance == null){
            return null;
        } else{
            return User.userInstance;
        }
    }

    /**
     * Gets an instance of the logged-in user. If there isn't one then set the instance to be a new user with the provided username and password.
     * @param userName
     *      The username of the user to be created if there isn't already a logged-in user
     * @param passWord
     *      The password of the user to be created if there isn't already a logged-in user
     * @return
     *      Returns the logged-in user object
     */
    public synchronized static User getInstance(String userName, String passWord){
        if(User.userInstance == null){
            User.userInstance = new User(userName, passWord);
            return User.userInstance;
        } else{
            return User.userInstance;
        }
    }

    /**
     * Gets an instance of the logged-in user. If there isn't one then set the instance to be a new user with all the provided attributes
     * Intended to be used when loading a pre-existing user object fom the database
     * May not actually be a good idea, but oh well
     * @param userName
     *      The username of the user to be created if there isn't already a logged-in user
     * @param passWord
     *      The password of the user to be created if there isn't already a logged-in user
     * @param email
     *      The email address of the user to be created if there isn't already a logged-in user
     * @param phonenumber
     *      The phone number of the user to be created if there isn't already a logged-in user
     * @param driver
     *      The driver object associated with the user to be created if there isn't already a logged-in user
     * @param rider
     *      The rider object associated with the user to be created if there isn't already a logged-in user
     * @return
     */
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
