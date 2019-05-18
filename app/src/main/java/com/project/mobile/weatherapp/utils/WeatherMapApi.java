package com.project.mobile.weatherapp.utils;


import com.google.gson.Gson;
import com.project.mobile.weatherapp.model.OpenWeatherMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherMapApi {

    public static OpenWeatherMap getWeatherInfor(double lat, double lon) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            URL url = new URL(Constants.OPEN_WEATHER_MAP_URL + "weather?lat=" + lat
                    + "&lon=" + lon + "&APPID=" + Constants.OPEN_WEATHER_MAP_API_KEY);
            con = (HttpURLConnection) (url).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            is = con.getInputStream();
            Reader targetReader = new InputStreamReader(is);
            OpenWeatherMap results = new Gson().fromJson(targetReader, OpenWeatherMap.class);
            return results;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
        return null;
    }
    public static OpenWeatherMap getWeatherInfor(String q) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/" + "weather?q=" + q +
                    "&APPID=" +
                    Constants.OPEN_WEATHER_MAP_API_KEY);
            con = (HttpURLConnection) (url).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            is = con.getInputStream();
            Reader targetReader = new InputStreamReader(is);
            OpenWeatherMap results = new Gson().fromJson(targetReader, OpenWeatherMap.class);
            return results;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
        return null;
    }
    public static OpenWeatherMap getWeatherDailyInfor(double lat, double lon) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            URL url = new URL(Constants.OPEN_WEATHER_MAP_URL + "forecast?lat=" + lat
                    + "&lon=" + lon + "&APPID=" + Constants.OPEN_WEATHER_MAP_API_KEY);
            con = (HttpURLConnection) (url).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            is = con.getInputStream();
            Reader targetReader = new InputStreamReader(is);
            OpenWeatherMap results = new Gson().fromJson(targetReader, OpenWeatherMap.class);
            return results;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
        return null;
    }
    public static OpenWeatherMap getWeatherDailyInfor(String q) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/" + "forecast?q=" + q +
                    "&APPID=" +
                    Constants.OPEN_WEATHER_MAP_API_KEY);
            con = (HttpURLConnection) (url).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            is = con.getInputStream();
            Reader targetReader = new InputStreamReader(is);
            OpenWeatherMap results = new Gson().fromJson(targetReader, OpenWeatherMap.class);
            return results;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
        return null;
    }
}
