package com.project.mobile.weatherapp.model.airvisual;

public class AirVisual {
    private Current current;
    private Data data;
    private Location location;
    private Pollution pollution;

    public AirVisual(Current current, Data data, Location location, Pollution pollution) {
        this.current = current;
        this.data = data;
        this.location = location;
        this.pollution = pollution;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
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

    public Pollution getPollution() {
        return pollution;
    }

    public void setPollution(Pollution pollution) {
        this.pollution = pollution;
    }
}
