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
        int a = 0;
    }

    public String requestStatus(){
        return "choo choo";
    }

    public int getNegativeRatings(){
        return this.negativeRatings;
    }

    public int getPositiveRatings(){
        return this.positiveRatings;
    }

    public Request getCurrentRequest(){
        return this.currentRequest;
    }

    public String scanQR(){
        return "scan failed";
    }
}
