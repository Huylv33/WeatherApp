package com.project.mobile.weatherapp.model.open_weather_map;

public class ListOfWeather {
    private Weather weather;
    private Main main;
    private  Clouds clouds;
    private Wind wind;
    private String dt_txt;
    private double temp_max;
    private double temp_min;

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public Main getMain() {
        return main;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public Weather getWeather() {
        return weather;
    }
}
