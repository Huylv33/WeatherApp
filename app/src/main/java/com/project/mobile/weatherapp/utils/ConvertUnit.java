package com.project.mobile.weatherapp.utils;

import com.project.mobile.weatherapp.model.open_weather_map.List;
import com.project.mobile.weatherapp.model.open_weather_map.ListOfWeather;
import com.project.mobile.weatherapp.model.open_weather_map.Main;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeather5Days3Hours;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherHours;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherPredict;
import com.project.mobile.weatherapp.model.open_weather_map.Rain;

public class ConvertUnit {
    public Boolean usingCelcius;
    public float rainRatio;


    public ConvertUnit(Boolean usingCelcius, float rainRatio) {

        this.rainRatio = rainRatio;
        this.usingCelcius = usingCelcius;
    }

    public void convert(OpenWeatherMap openWeatherMap) {
        Main main = openWeatherMap.getMain();
        if(this.usingCelcius) {
            main.setTemp(main.getTemp() - 273.15);
            main.setTemp_max(main.getTemp_max() - 273.15);
            main.setTemp_min(main.getTemp_min() - 273.15);
        }
        else {
            main.setTemp(32 + 1.8 * (main.getTemp() - 273.15));
            main.setTemp_max(32 + 1.8 * (main.getTemp_max() - 273.15));
            main.setTemp_min(32 + 1.8 * (main.getTemp_min() - 273.15));
        }
        openWeatherMap.setMain(main);
        if(openWeatherMap.getRain() != null) {

        }

    }

    public void convert(OpenWeatherPredict openWeatherPredict) {
        java.util.List<ListOfWeather> list = openWeatherPredict.getListWeather();
        for ( ListOfWeather list1 : list ) {
            Main main = list1.getMain();
            double temp_max = list1.getTemp_max();
            double temp_min = list1.getTemp_min();
            if(this.usingCelcius) {
                main.setTemp(main.getTemp() - 273.15);
                main.setTemp_min(main.getTemp_min() - 273.15);
                main.setTemp_max(main.getTemp_max() - 273.15);
                temp_max = list1.getTemp_max() - 273.15;
                temp_min = list1.getTemp_min() - 273.15;
            }
            else {
                main.setTemp(32 + 1.8 * (main.getTemp() - 273.15));
                main.setTemp_max(32 + 1.8 * (main.getTemp_max() - 273.15));
                main.setTemp_min(32 + 1.8 * (main.getTemp_min() - 273.15));
                temp_max = 32 + 1.8 * (list1.getTemp_max() - 273.15);
                temp_min = 32 + 1.8 * (list1.getTemp_min() - 273.15);
            }
            list1.setMain(main);
            list1.setTemp_max(temp_max);
            list1.setTemp_min(temp_min);
        }
    }

    public void convert(OpenWeatherHours openWeatherHours) {
        java.util.List<List> lists = openWeatherHours.list;
        for (List list : lists) {
            Main main = list.getMain();
            if(this.usingCelcius) {
                main.setTemp(main.getTemp() - 273.15);
                main.setTemp_max(main.getTemp_max() - 273.15);
                main.setTemp_min(main.getTemp_min() - 273.15);
            }
            else {
                main.setTemp(32 + 1.8 * (main.getTemp() - 273.15));
                main.setTemp_max(32 + 1.8 * (main.getTemp_max() - 273.15));
                main.setTemp_min(32 + 1.8 * (main.getTemp_min() - 273.15));
            }
            list.setMain(main);
        }
    }
}
