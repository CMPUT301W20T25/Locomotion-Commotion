package com.example.locomotioncommotion;

/**
 * Rider
 * Keeps track of Rider information and methods for it
 * */

public class Rider {
    private Request currentRequest;

    /**
     * Constructor for the rider class
     * Starts with their current request as null
     */
    public Rider(){
        this.currentRequest = null;
    }

//    /**
//     * Creates a new request for this rider
//     * @param start
//     *      The start location of the request
//     * @param end
//     *      The final destination of the request
//     * @param fare
//     *      The amount of money to be offered for the completion of the request
//     * @return
//     *      A request object with the above parameters and the issuing rider as attributes
//     */
//    public Request createRequest(String start, String end, Integer fare){
//        return new Request(this, start, end, fare);
//    }

    /**
     * Gets the current request of the rider
     * @return
     *      The rider's current request
     */
    public Request getCurrentRequest(){
        return this.currentRequest;
    }

    /**
     * Sets the current request of the rider
     * @param request
     *      The rider's new current request
     */
    public void setCurrentRequest(Request request){
        this.currentRequest = request;
    }

    /**
     * Sets the rider's current request status to complete
     */
    public void notifyRequestComplete(){
        if(this.currentRequest == null){
            throw new NullPointerException("currentrequest cannot be null");
        } else {
            this.currentRequest.setStatus("Completed");
            this.currentRequest = null;
        }
    }

    /**
     * Sets the rider's current request status to "Cancelled" and delinks it from the rider
     */
    public void cancelRequest(){
        if(this.currentRequest == null){
            throw new NullPointerException("currentrequest cannot be null");
        } else {
            this.currentRequest.setStatus("Cancelled");
            this.currentRequest = null;
        }
    }

    /**
     * Returns the status of the rider's current request
     * @return
     *      The status of the rider's current request, or "no active request" if the rider lacks such a request
     */
    public String requestStatus(){
        if(this.getCurrentRequest() == null){
            return "No active request";
        } else{
            return this.getCurrentRequest().getStatus();
        }

    }

    /**
     * Generates a QRCode
     * //TODO: Turn this stub into a real function
     * @return
     */
    public QRCode generateQRPrice(){
        return null;
    }

}
