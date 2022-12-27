package com.example.weatherforecast;

public class Place {
    private String name;
    private String lat;
    private String lon;

    public Place(String name, String lat, String lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
