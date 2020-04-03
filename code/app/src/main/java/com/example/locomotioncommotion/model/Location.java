package com.example.locomotioncommotion.model;

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

    /**
     * Returns the distance between two locations
     * @param location
     *      Another location that you want to find the distance to
     * @return
     *      distance between two locations
     */
    public int distance(Location location){
        double lat1 = Math.toRadians(lat);
        double lng1 = Math.toRadians(lng);
        double lat2 = Math.toRadians(location.getLat());
        double lng2 = Math.toRadians(location.getLng());
        int radius = 6371000;

        double term1 = Math.pow(Math.sin((lat2 - lat1) / 2), 2);
        double term2 = Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lng2 - lng1) / 2), 2);

        int distance = (int) (2 * radius * Math.asin(Math.sqrt(term1 + term2)));

        return distance;
    }

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
