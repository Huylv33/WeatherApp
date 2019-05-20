package com.project.mobile.weatherapp.model.open_weather_map;

import java.util.List;

public class OpenWeather5Days3Hours {
    private City city;
    private Coord coord;
    private String country;
    private int cod;
    private float message;
    private int cnt;
    private List<ListWeather> list;

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

    public void setList(List<ListWeather> list) {
        this.list = list;
    }

    public List<ListWeather> getList() {
        return list;
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
