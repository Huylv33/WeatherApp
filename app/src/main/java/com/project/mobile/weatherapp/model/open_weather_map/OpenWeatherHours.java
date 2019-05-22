package com.project.mobile.weatherapp.model.open_weather_map;

public class OpenWeatherHours {
    public java.util.List<List> list;

    public OpenWeatherHours(OpenWeather5Days3Hours openWeather5Days3Hours) {
        for(int i = 0; i < 8; i ++) {
            for(int j = 0; j < 3; j++) {
                list.add(openWeather5Days3Hours.getList().get(i));
            }
        }
    }
}
