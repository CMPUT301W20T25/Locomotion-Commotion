/**
 * Driver
 *
 * This class holds the information related to a driver
 * it is not implemented at the moment
 */
package com.example.locomotioncommotion.model;

/**
 * The driver class keep's track of all of a user's driver related information
 */
public class Driver {

    private int positiveRatings;
    private int negativeRatings;
    private Request currentRequest;

    /**
     * Constructs a new empty driver, with no current request or ratings
     */
    public Driver(){
        this.positiveRatings = 0;
        this.negativeRatings = 0;
        this.currentRequest = null;
    }

    /**
     * Accepts a given request, setting it as the current request of the driver
     * @param request
     *      The request to be accepted
     */
    public void acceptRequest(Request request){
        this.currentRequest = request;
        this.currentRequest.setStatus("Accepted");
    }

    /**
     * Requests the status of the current request of the driver, and returns "no active request" if there is no such request
     * @return
     *      The request status of the current request, or "no active request"
     */
    public String requestStatus(){
        if(this.currentRequest == null) {
            //return "No active request";
            return null;
        } else{
            return this.currentRequest.getStatus();
        }
    }

    /**
     * Gets the total negative ratings of the driver
     * @return
     *      The number of negative ratings that the driver has received
     */
    public int getNegativeRatings(){ return this.negativeRatings; }

    /**
     * Sets the total negative ratings of the driver
     * @param rating
     *      The new number of negative ratings that the driver has
     */
    public void setNegativeRatings(int rating) {this.negativeRatings = rating;}

    /**
     * Gets the total positive ratings that the driver has received
     * @return
     *      The number of positive ratings that the driver has received
     */
    public int getPositiveRatings(){ return this.positiveRatings; }

    /**
     * Sets the total amount of positive ratings that the driver has received
     * @param rating
     *      The number of postive ratings that the driver is to have
     */
    public void setPositiveRatings(int rating) {this.positiveRatings = rating;}

    /**
     * Returns the current request of the driver
     * @return
     *      The current request of the driver
     */
    public Request getCurrentRequest(){ return this.currentRequest; }
}
