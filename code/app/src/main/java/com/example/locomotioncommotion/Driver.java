/**
 * Driver
 *
 * This class holds the information related to a driver
 * it is not implemented at the moment
 */
package com.example.locomotioncommotion;
public class Driver {

    private int positiveRatings;
    private int negativeRatings;
    private Request currentRequest;

    public Driver(){
        this.positiveRatings = 0;
        this.negativeRatings = 0;
        this.currentRequest = null;
    }

    public void acceptRequest(Request request){
        this.currentRequest = request;
        //TODO: Check that there isn't already a current request
        //TODO: Update the status of the accepted request
    }

    public String requestStatus(){
        if(this.currentRequest == null) {
            return "No active request";
        } else{
            return this.currentRequest.getStatus();
        }
    }

    public int getNegativeRatings(){
        return this.negativeRatings;
    }

    public void setNegativeRatings(int rating) {this.negativeRatings = rating;}

    public int getPositiveRatings(){
        return this.positiveRatings;
    }

    public void setPositiveRatings(int rating) {this.positiveRatings = rating;}

    public Request getCurrentRequest(){
        return this.currentRequest;
    }

    public String scanQR(){
        return "scan failed";
    }
}
