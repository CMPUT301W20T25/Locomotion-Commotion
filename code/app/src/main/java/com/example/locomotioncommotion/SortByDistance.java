package com.example.locomotioncommotion;

import java.util.Comparator;

public class SortByDistance implements Comparator<Request> {
    private Location start;

    public SortByDistance(Location location){
        start = location;
    }

    public int compare(Request r1, Request r2){
        Location end1 = r1.getStartLocation();
        Location end2 = r2.getStartLocation();

        int d1 = start.distance(end1);
        int d2 = start.distance(end2);

        return (d1 - d2);
    }
}
