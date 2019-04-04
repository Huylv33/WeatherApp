package com.project.mobile.weatherapp.utils;
import com.google.gson.Gson;
import com.project.mobile.weatherapp.model.OpenWeatherMap;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherMapApi {
    public static OpenWeatherMap prediction(double lat, double lon) {
        try {

            URL url = new URL(Constants.OPEN_WEATHER_MAP_URL + "weather?" +lat
                    +"&amp;lon="+lon);
            InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");
            OpenWeatherMap results = new Gson().fromJson(reader, OpenWeatherMap.class);
            String idIcon = results.getWeather().get(0).getIcon().toString();
            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
            URL urlImage = new URL(urlIcon);
            return results;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
