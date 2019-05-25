package com.project.mobile.weatherapp.model.open_weather_map;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OpenWeatherHours {
    public java.util.List<List> list;

    public OpenWeatherHours(OpenWeather5Days3Hours openWeather5Days3Hours) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        java.util.List<List> listOfWeatherHourly = new ArrayList<List>();
        for(int i = 0; i < openWeather5Days3Hours.getList().size(); i ++) {
                listOfWeatherHourly.add(openWeather5Days3Hours.getList().get(i));
                String dt_txt = listOfWeatherHourly.get(i).getDt_txt();
        }
        this.list = listOfWeatherHourly;
    }
}

