package com.project.mobile.weatherapp.utils;

import com.google.gson.Gson;
import com.project.mobile.weatherapp.model.airvisual.AirVisual;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AirVisualApi {
    public AirVisual getNearestCityData(double lat, double lon) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            URL url = new URL("api.airvisual.com/v2/nearest_city?" + "lat=" + lat
            + "&lon=" + lon + "&key=" + Constants.AIR_VISUAL_API_KEY);
            con = (HttpURLConnection) (url).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            is = con.getInputStream();
            Reader targetReader = new InputStreamReader(is);
            AirVisual results = new Gson().fromJson(targetReader, AirVisual.class);
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
