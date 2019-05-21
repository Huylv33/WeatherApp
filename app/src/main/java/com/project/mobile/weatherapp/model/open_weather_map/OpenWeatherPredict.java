package com.project.mobile.weatherapp.model.open_weather_map;

import java.util.ArrayList;
import java.util.List;

public class OpenWeatherPredict {
    private java.util.List<ListOfWeather> listOfWeather;

    public java.util.List<ListOfWeather> getListWeather() {
        return listOfWeather;
    }

    public void setListWeather(java.util.List<ListOfWeather> listOfWeather) {
        this.listOfWeather = listOfWeather;
    }

    public OpenWeatherPredict(OpenWeather5Days3Hours openWeather5Days3Hours) {
        java.util.List<ListOfWeather> weather5Days = new ArrayList<ListOfWeather>();

        for( int i = 0; i < 40; i += 5) {
            double temp_max = 0;
            double temp_min = 0;
            for(int j = i; j < i + 8; j++) {
                if (openWeather5Days3Hours.getList().get(j).getMain().getTemp_max() > temp_max) {
                    temp_max = openWeather5Days3Hours.getList().get(0).getMain().getTemp_max();
                }
                if (openWeather5Days3Hours.getList().get(j).getMain().getTemp_min() < temp_min) {
                    temp_min = openWeather5Days3Hours.getList().get(j).getMain().getTemp_min();
                }
            }
            weather5Days.get(i).setTemp_max(temp_max);
            weather5Days.get(i).setTemp_min(temp_min);
            weather5Days.get(i).setClouds(openWeather5Days3Hours.getList().get(i + 2).getClouds());
            weather5Days.get(i).setMain(openWeather5Days3Hours.getList().get(i + 2).getMain());
            weather5Days.get(i).setDt_txt(openWeather5Days3Hours.getList().get(i + 2).getDt_txt());
            weather5Days.get(i).setWeather(openWeather5Days3Hours.getList().get(i + 2).getWeather());
            weather5Days.get(i).setWind(openWeather5Days3Hours.getList().get(i + 2).getWind());

        }
        this.setListWeather(weather5Days);
    }
}


