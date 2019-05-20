package com.project.mobile.weatherapp.model.open_weather_map;

import java.util.List;

public class ListWeather {
    private double dt;
    private Main main;
    private Weather weather;
    private Clouds clouds;
    private Wind wind;
    private Sys sys;
    private String dt_txt;

    public Clouds getClouds() {
        return clouds;
    }

    public double getDt() {
        return dt;
    }

    public Weather getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Wind getWind() {
        return wind;
    }
}
