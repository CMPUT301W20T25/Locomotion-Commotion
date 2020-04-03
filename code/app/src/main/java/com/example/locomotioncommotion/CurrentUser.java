package com.example.locomotioncommotion;

//Singleton implementation based on
//https://stackoverflow.com/a/12587124

/**
 * Current User
 * A singleton designed to keep track of the currently logged in User object
 */
public class CurrentUser {
    private static CurrentUser currentUserInstance;
    private User user;

    /**
     * Makes an instance of
     * @param user
     *      The User object that corresponds to the logged-in user
     */
    private CurrentUser(User user){
        this.user = user;
    }

    /**
     * Gets an instance of the logged-in user. If there isn't one then return null, since a user with no username makes no sense
     * @return
     *      Returns the logged-in user object, or null if there is no logged-in user
     */
    public synchronized static CurrentUser getInstance(){
        if(CurrentUser.currentUserInstance == null){
            return null;
        } else{
            return CurrentUser.currentUserInstance;
        }
    }

    /**
     * Gets an instance of the logged-in user. If there isn't one then set the instance to be a new user with the provided username and password.
     * @param user
     *      The user object that should be assigned to the logged-in user if there is not currently a logged-in user
     * @return
     *      Returns the logged-in user object
     */
    public synchronized static CurrentUser getInstance(User user){
        if(CurrentUser.currentUserInstance == null){
            CurrentUser.currentUserInstance = new CurrentUser(user);
            return CurrentUser.currentUserInstance;
        } else{
            return CurrentUser.currentUserInstance;
        }
    }

    /**
     * Getter for the .user attribute
     * @return
     *      Returns the user object associated with this CurrentUser object
     */
    public User getUser(){
        return this.user;
    }

    /**
     * Setter for the .user attribute
     * @param user
     *      The new user to be assigned to the current user
     */
    public void setUser(User user){
        this.user = user;
    }
}
