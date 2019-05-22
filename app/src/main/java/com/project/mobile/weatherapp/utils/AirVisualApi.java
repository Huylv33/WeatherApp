package com.project.mobile.weatherapp.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.project.mobile.weatherapp.model.airvisual.AirVisual;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AirVisualApi {
    public static AirVisual getNearestCityData(double lat, double lon) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            String requestUrl = "https://api.airvisual.com/v2/nearest_city?" + "lat=" + lat
                    + "&lon=" + lon + "&key=" + Constants.AIR_VISUAL_API_KEY;
            Log.i("requesturl",requestUrl);
            URL url = new URL(requestUrl);
            con = (HttpURLConnection) (url).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
//            con.setDoOutput(true);
            con.connect();
            is = con.getInputStream();
            Reader targetReader = new InputStreamReader(is);
            AirVisual results = new Gson().fromJson(targetReader, AirVisual.class);
            if (results ==  null) {
                Log.d("airvisual","null");
            }
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
    public static AirVisual getNearestCityData(String city, String state, String country) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            String requestUrl = "https://api.airvisual.com/v2/nearest_city?" + "city=" + city
                    + "&state=" + state + "&country=" + country + "&key=" + Constants.AIR_VISUAL_API_KEY;
            URL url = new URL(requestUrl);
            con = (HttpURLConnection) (url).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
//            con.setDoOutput(true);
            con.connect();
            is = con.getInputStream();
            Reader targetReader = new InputStreamReader(is);
            AirVisual results = new Gson().fromJson(targetReader, AirVisual.class);
            if (results ==  null) {
                Log.d("airvisual","null");
            }
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
