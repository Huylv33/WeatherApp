package com.project.mobile.weatherapp.fragment;

import java.io.Serializable;

public class DataCommunication implements Serializable {
    private double lat;
    private double lon;
    private String q;

    public DataCommunication(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public DataCommunication(String q) {
        this.q = q;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getQ() {
        return q;
    }
}
