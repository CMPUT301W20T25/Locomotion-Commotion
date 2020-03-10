package com.example.locomotioncommotion;

public class Request {
    private Rider currentRider;
    private Driver currentDriver;
    private Location startLocation;
    private Location endLocation;
    private int fareOffered;
    private String status;

    public Request(Rider rider, Location startLocation, Location endLocation, int fareOffered){
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

    public Location getStartLocation(){
        return this.startLocation;
    }

    public Location getEndLocation(){
        return this.endLocation;
    }

    //Not actually sure if these location setters are needed, but just in case here they are
    public void setStartLocation(Location location){
        this.startLocation = location;
    }

    public void setEndLocation(Location location){
        this.endLocation = location;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
