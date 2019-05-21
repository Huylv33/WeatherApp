package com.project.mobile.weatherapp.model.open_weather_map;

public class OpenWeather5Days3Hours {
    private City city;
    private Coord coord;
    private String country;
    private int cod;
    private float message;
    private int cnt;
    private java.util.List<List> list;

    public City getCity() {
        return city;
    }

    public Coord getCoord() {
        return coord;
    }

    public float getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public int getCod() {
        return cod;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public String getCountry() {
        return country;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setMessage(float message) {
        this.message = message;
    }

}
