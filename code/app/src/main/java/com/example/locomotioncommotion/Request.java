package com.example.locomotioncommotion;

/**
 * Request
 * keeping track of all information about rider requests
 */
public class Request {
    private Rider currentRider;
    private Driver currentDriver;
    private String startLocation;
    private String endLocation;
    private int fareOffered;
    private String status;

    public Request() {
        this.currentRider = null;
        this.currentDriver = null;
        this.startLocation = null;
        this.endLocation = null;
        this.fareOffered = 0;
        this.status = "Pending"; //TODO: check that this makes sense
    }
    public Request(Rider rider, String startLocation, String endLocation, int fareOffered){
        this.currentRider = rider;
        this.currentDriver = null;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fareOffered = fareOffered;
        this.status = "Pending"; //TODO: check that this makes sense
    }

    public Rider getCurrentRider(){
        return this.currentRider;
    }

    public void setCurrentRider(Rider rider){
        this.currentRider = rider;
    }

    public Driver getCurrentDriver(){
        return this.currentDriver;
    }

    public void setCurrentDriver(Driver driver){
        this.currentDriver = driver;
    }

    public String getStartLocation(){
        return this.startLocation;
    }

    public String getEndLocation(){
        return this.endLocation;
    }

    //Not actually sure if these location setters are needed, but just in case here they are
    public void setStartLocation(String location){
        this.startLocation = location;
    }

    public void setEndLocation(String location){
        this.endLocation = location;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
