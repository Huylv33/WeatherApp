package com.project.mobile.weatherapp.utils;

import android.os.AsyncTask;

import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherPredict;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Weather5DaysAsyncTask extends AsyncTask<Void,Void, OpenWeatherPredict> {
    private NumberFormat format = new DecimalFormat("#0.0");
    private  String q;
    private double lat, lon;
    public doComplete5Days finish = null;

    private TypePrediction typePrediction;

    public Weather5DaysAsyncTask(String q, doComplete5Days finish) {
        this.q = q;
        this.finish = finish;
        typePrediction = TypePrediction.ADDRESS_NAME;
    }
    public Weather5DaysAsyncTask(double lat, double lon, doComplete5Days finish) {
        this.lat = lat;
        this.lon = lon;
        this.finish = finish;
        typePrediction = TypePrediction.LATITUDE_LONGITUDE;
    }

    @Override
    protected OpenWeatherPredict doInBackground(Void... params) {
        OpenWeatherPredict openWeatherPredict = null;
        if (typePrediction == TypePrediction.ADDRESS_NAME){
            openWeatherPredict = WeatherMapApi.getWeather5Days(this.q);
        }
        else if (typePrediction == TypePrediction.LATITUDE_LONGITUDE){
            openWeatherPredict = WeatherMapApi.getWeather5Days(this.lat,this.lon);
        }
        return openWeatherPredict;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(OpenWeatherPredict openWeatherPredict) {
        super.onPostExecute(openWeatherPredict);
        finish.doComplete(openWeatherPredict);
    }
}
