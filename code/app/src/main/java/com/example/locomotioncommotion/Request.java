package com.example.locomotioncommotion;

/**
 * Request
 * keeping track of all information about rider requests
 */
public class Request {
    private User currentRider;
    private Driver currentDriver;
    private String startLocation;
    private String endLocation;
    private int fareOffered;
    private String status;
    private long timestamp;

    /**
     * Constructor for the request class. Just makes a blank request
     */
    public Request() {
        this.currentRider = null;
        this.currentDriver = null;
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
    public Request(User rider, String startLocation, String endLocation, int fareOffered){
        this.currentRider = rider;
        this.currentDriver = null;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fareOffered = fareOffered;
        this.status = "Pending"; //TODO: check that this makes sense
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Returns the rider currently attached to the request
     * @return
     *      The current rirder
     */
    public User getCurrentRider(){
        return this.currentRider;
    }

    /**
     * Sets the rider associated with the request
     * @param rider
     *      The new rider to associate with this request
     */
    public void setCurrentRider(User rider){
        this.currentRider = rider;
    }

    /**
     * Gets the driver currently associated with the request
     * @return
     *      The attached driver
     */
    public Driver getCurrentDriver(){
        return this.currentDriver;
    }
    /**
     * Sets the driver associated with the request
     * @param driver
     *      The new driver to associate with this request
     */
    public void setCurrentDriver(Driver driver){
        this.currentDriver = driver;
    }
    /**
     * Gets the start location of the request
     * @return
     *      The start location of the request
     */
    public String getStartLocation(){
        return this.startLocation;
    }

    /**
     * Gets the end location of the request
     * @return
     *      The end location of the request
     */
    public String getEndLocation(){
        return this.endLocation;
    }

    /**
     * Sets the start location of the request
     * @param location
     *      The new location to set as the request's start location
     */
    public void setStartLocation(String location){
        this.startLocation = location;
    }

    /**
     * Sets the end location of the request
     * @param location
     *      The new location to set as the request's end location
     */
    public void setEndLocation(String location){
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
     * Sets the timestamp used for sorting
     * @param timestamp
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * Gets the fare amount offered
     * @return
     */
    public int getFareOffered() {
        return this.fareOffered;
    }
}


