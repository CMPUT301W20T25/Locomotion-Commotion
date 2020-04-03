package com.example.locomotioncommotion.model;

import com.example.locomotioncommotion.model.Location;

import java.io.Serializable;
import java.util.ArrayList;

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
     * Constructor for the request class. Just makes a blank request
     */
    public Request() {
        this.riderUsername = null;
        this.driverUsername = null;
        this.startLocation = null;
        this.endLocation = null;
        this.fareOffered = 0;
        this.timestamp = 0;

        this.firebaseID = "";
        this.status = "Pending";
    }

    /**
     * Constructor for the request class. Takes all attributes but driver, status and timestamp.
     * Driver and status are set to null and pending, respectively, while timestamp is the current time
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
        this.timestamp = System.currentTimeMillis();

        this.firebaseID = "";
        this.status = "Pending";
    }
    /**
     * Constructor for the request class. Takes all attributes but driver and status
     * Driver and status are set to null and pending, respectively
     * @param rider
     *      The rider who issued the request
     * @param startLocation
     *      The start location of the request
     * @param endLocation
     *      The ultimate destination of the request
     * @param fareOffered
     *      The amount of money offered for this request
     * @param timestamp
     *      The timestamp that this request was first issued at
     */
    public Request(String rider, Location startLocation, Location endLocation, int fareOffered, long timestamp){
        this.riderUsername = rider;
        this.driverUsername = null;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fareOffered = fareOffered;
        this.timestamp = timestamp;

        this.firebaseID = "";
        this.status = "Pending";
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
     * Checks that the inputted string is a valid Request status code
     * Valid status codes for Requests and their meanings:
     * Pending: The request has been created by the Rider, but no Driver has accepted it
     * Accepted: The request has been accepted by a Driver. but not confirmed by the Rider that issued it
     * Confirmed: The request has been accepted by a Driver, and the Rider that issued it has confirmed that acceptance
     * Completed: The request has been successfully completed
     * Cancelled: The request has been prematurely deleted by the Rider
     * @return
     *      Whether the inputted string is a valid Request status code
     */

    public Boolean checkStatusCodeValidity(String status){
        ArrayList<String> validStatuses = new ArrayList<String>();
        validStatuses.add("Pending");
        validStatuses.add("Accepted");
        validStatuses.add("Confirmed");
        validStatuses.add("Completed");
        validStatuses.add("Cancelled");

        return validStatuses.contains(status);
    }

    /**
     * Sets the status of the request
     * @param status
     *      The new status of the request
     */
    public void setStatus(String status){
        if(checkStatusCodeValidity(status)) {
            this.status = status;
        } else{
            throw new IllegalArgumentException("status must be on the list of allowed statuses");
        }
    }

    /**
     * Gets the UNIX timestamp from when the request was made
     * @return
     *      Returns the UNIX timestamp from when the request was made
     */
    public long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Gets the fare amount offered
     * @return
     *      Returns the fare amount of the request, in cents
     */
    public int getFareOffered() {
        return this.fareOffered;
    }

    /**
     * Gets the firebase ID for the request
     * @return
     *      Returns the firebase ID of the request
     */
    public String getFirebaseID() {return this.firebaseID; }

    /**
     * Sets the firebase ID for the request
     * @param id
     *      A string that represents the ID of the firebase this request is stored in
     */
    public void setFirebaseID(String id) {this.firebaseID = id; }

    public int calculateCost(){
        return (int) (0.002 * startLocation.distance(endLocation) + 5);
    }
}


