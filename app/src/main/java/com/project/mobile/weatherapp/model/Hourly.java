package com.project.mobile.weatherapp.model;

import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherHours;

public class Hourly {
    public com.project.mobile.weatherapp.model.open_weather_map.List list;
    private String mTextTime;
    private String mTextTemp;
    private String mTextWind;
    private String mTextHumidity;
    private String WeatherIcon;
    private String mTextWeather;
    private String mTextPressure;
    public  Hourly (String mTextTime, String mTextTemp, String mTextWind, String mTextHumidity, String mTextWeather, String mTextPressure) {
        this.mTextTime = mTextTime;
        this.mTextTemp = mTextTemp;
        this.mTextWind = mTextWind;
        this.mTextHumidity = mTextHumidity;
        this.mTextWeather = mTextWeather;
        this.mTextPressure = mTextPressure;
    }
    public Hourly() {

    }

    public  String getmTextTime() {
        return mTextTime;
    }

    public String getWeatherIcon() {
        return WeatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        WeatherIcon = weatherIcon;
    }

    public  void setmTextTime(String mTextTime) {
        this.mTextTime = mTextTime;
    }

    public  String getmTextTemp() {
        return mTextTemp;
    }

    public  void setmTextTemp(String mTextTemp) {
        this.mTextTemp = mTextTemp;
    }

    public  String getmTextWind() {
        return mTextWind;
    }

    public  void setmTextWind(String mTextWind) {
        this.mTextWind = mTextWind;
    }

    public  String getmTextHumidity() {
        return mTextHumidity;
    }

    public  void setmTextHumidity(String mTextHumidity) {
        this.mTextHumidity = mTextHumidity;
    }

    public  String getmTextWeather() {
        return mTextWeather;
    }

    public  void setmTextWeather(String mTextWeather) {
        this.mTextWeather = mTextWeather;
    }

    public  String getmTextPressure() {
        return mTextPressure;
    }

    public  void setmTextPressure(String mTextPressure) {
        this.mTextPressure = mTextPressure;
    }
}

