package com.example.locomotioncommotion;

import java.io.Serializable;

/**
 * Location
 * class for Location data.
 * mostly stubs
 */
public class Location implements Serializable {
    private double lat;
    private double lng;
    private String name;

    public Location() {
        this.lat = 0;
        this.lng = 0;
        this.name = null;
    }

    public Location(double lat, double lng, String name){
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }

//    /**
//     * Returns the distance between two locations
//     * @param location
//     *      Another location that you want to find the distance to
//     * @return
//     *      distance between two locations
//     */
//    public float distance(Location location){
//        return this.x - location.x;
//    }

    public double getLat() {
        return this.lat;
    }

    public double getLng() {
        return this.lng;
    }

    public String getName() {
        return this.name;
    }
}
