package com.project.mobile.weatherapp.model.airvisual;

public class AirVisual {
    private String status;
    private Data data;
    private Location location;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AirVisual(Data data, Location location) {
        this.data = data;
        this.location = location;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
