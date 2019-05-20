package com.project.mobile.weatherapp.utils;

import android.os.AsyncTask;

import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WeatherAsyncTask extends AsyncTask<Void,Void,OpenWeatherMap> {
    private NumberFormat format = new DecimalFormat("#0.0");
    private  String q;
    private double lat, lon;
    public doComplete finish = null;

    private TypePrediction typePrediction;

    public WeatherAsyncTask(String q, doComplete finish) {
        this.q = q;
        this.finish = finish;
        typePrediction = TypePrediction.ADDRESS_NAME;
    }
    public WeatherAsyncTask(double lat, double lon, doComplete finish) {
        this.lat = lat;
        this.lon = lon;
        this.finish = finish;
        typePrediction = TypePrediction.LATITUDE_LONGITUDE;
    }

    @Override
    protected OpenWeatherMap doInBackground(Void... params) {
        OpenWeatherMap openWeatherMap = null;
        if (typePrediction == TypePrediction.ADDRESS_NAME){
            openWeatherMap = WeatherMapApi.getWeatherInfor(this.q);
        }
        else if (typePrediction == TypePrediction.LATITUDE_LONGITUDE){
            openWeatherMap = WeatherMapApi.getWeatherInfor(this.lat,this.lon);
        }
        return openWeatherMap;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(OpenWeatherMap openWeatherMap) {
        super.onPostExecute(openWeatherMap);
        finish.doCompele(openWeatherMap);
    }

}