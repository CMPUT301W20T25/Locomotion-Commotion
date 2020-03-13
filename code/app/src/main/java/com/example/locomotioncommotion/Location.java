package com.example.locomotioncommotion;

/**
 * Location
 * class for Location data.
 * mostly stubs
 */
public class Location {
    private int x;

    public Location() {
        this.x =0;
    }
    public Location(int x){
        this.x = x;
    }

    /**
     * Returns the distance between two locations
     * @param location
     *      Another location that you want to find the distance to
     * @return
     *      distance between two locations
     */
    public float distance(Location location){
        return this.x - location.x;
    }
}
