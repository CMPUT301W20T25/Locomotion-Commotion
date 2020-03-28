package com.example.locomotioncommotion;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.Serializable;

/**
 * Request
 * keeping track of all information about rider requests
 */
public class Request implements Serializable {
    private String riderUsername;
    private String driverUsername;
    private Location startLocation;
    private Location endLocation;
    private int fareOffered;
    private String status;
    private long timestamp;
    private String firebaseID;

    /**
     * test constructor temporary
     */
    public Request(Location startLocation, Location endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    /**
     * Constructor for the request class. Just makes a blank request
     */
    public Request() {
        this.riderUsername = null;
        this.driverUsername = null;
        this.startLocation = null;
        this.endLocation = null;
        this.fareOffered = 0;
        this.status = "Pending"; //TODO: check that this makes sense
    }

    /**
     * Constructor for the request class. Takes all attributes but driver and status.
     * Driver and status are set to null and pending, respectively
     * @param rider
     *      The rider who issued the request
     * @param startLocation
     *      The start location of the request
     * @param endLocation
     *      The ultimate destination of the request
     * @param fareOffered
     *      The amount of money offered for this request
     */
    public Request(String rider, Location startLocation, Location endLocation, int fareOffered){
        this.riderUsername = rider;
        this.driverUsername = null;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fareOffered = fareOffered;
        this.status = "Pending"; //TODO: check that this makes sense
        this.timestamp = System.currentTimeMillis();
    }

    public Request(String rider, Location startLocation, Location endLocation, int fareOffered, long timestamp){
        this.riderUsername = rider;
        this.driverUsername = null;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fareOffered = fareOffered;
        this.status = "Pending"; //TODO: check that this makes sense
        this.timestamp = timestamp;
    }

    /**
     * Notifies the rider that their ride request is progressing
     * Function called in some kind of thingy that interfaces with the database
     * @param notificationTitle
     *      The title of generated notification
     * @param notificationMessage
     *      The message of the generated notification
     * @param context
     *      The context that the notification is to be put to
     */
    public void notifyRider(String notificationTitle, String notificationMessage, Context context){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "LocomotionCommotion")
                //.setSmallIcon(R.drawable.notification_icon) TODO: Add an icon for this
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Just make the notification go away when you tap it for now
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build()); //ID = 1 for rider notification
    }

    public void notifyDriver(String notificationTitle, String notificationMessage, Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "LocomotionCommotion")
                //.setSmallIcon(R.drawable.notification_icon) TODO: Add an icon for this
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Just make the notification go away when you tap it for now
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(2, builder.build()); //ID = 2 for driver notification
    }

    /**
     * Returns the rider currently attached to the request
     * @return
     *      The current rider
     */
    public String getRiderUsername(){
        return this.riderUsername;
    }

    /**
     * Sets the rider associated with the request
     * @param rider
     *      The new rider to associate with this request
     */
    public void setRiderUsername(String rider){
        this.riderUsername = rider;
    }

    /**
     * Gets the driver currently associated with the request
     * @return
     *      The attached driver
     */
    public String getDriverUsername(){
        return this.driverUsername;
    }
    /**
     * Sets the driver associated with the request
     * @param driver
     *      The new driver to associate with this request
     */
    public void setDriverUsername(String driver){
        this.driverUsername = driver;
    }
    /**
     * Gets the start location of the request
     * @return
     *      The start location of the request
     */
    public Location getStartLocation(){
        return this.startLocation;
    }

    /**
     * Gets the end location of the request
     * @return
     *      The end location of the request
     */
    public Location getEndLocation(){
        return this.endLocation;
    }

    /**
     * Sets the start location of the request
     * @param location
     *      The new location to set as the request's start location
     */
    public void setStartLocation(Location location){
        this.startLocation = location;
    }

    /**
     * Sets the end location of the request
     * @param location
     *      The new location to set as the request's end location
     */
    public void setEndLocation(Location location){
        this.endLocation = location;
    }

    /**
     * Gets the status of the request
     * @return
     *      The status of the request
     */
    public String getStatus(){
        return this.status;
    }

    /**
     * Sets the status of the request
     * TODO: Maybe enforce only a few allowed strings as valid request statuses?
     * @param status
     *      The new status of the request
     */
    public void setStatus(String status){
        this.status = status;
    }

    /**
     * Gets the UNIX timestamp from when the request was made
     * @return
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Gets the fare amount offered
     * @return
     */
    public int getFareOffered() {
        return this.fareOffered;
    }

    public String getFirebaseID() {return this.firebaseID; }

    public void setFirebaseID(String id) {this.firebaseID = id; }
}


