package com.project.mobile.weatherapp.utils;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.project.mobile.weatherapp.adapter.DailyAdapter;
import com.project.mobile.weatherapp.model.Daily;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherPredict;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Weather5DaysAsyncTask extends AsyncTask<Void,Void, OpenWeatherPredict> {
    private NumberFormat format = new DecimalFormat("#0.0");
    public DailyAdapter mAdapter;
    public RecyclerView recyclerView;

    private  String q;
    private double lat, lon;
    public doComplete5Days finish = null;

    private TypePrediction typePrediction;

    public Weather5DaysAsyncTask(String q,DailyAdapter mAdapter,RecyclerView recyclerView, doComplete5Days finish) {
        this.q = q;
        this.finish = finish;
        this.mAdapter = mAdapter;
        typePrediction = TypePrediction.ADDRESS_NAME;
    }
    public Weather5DaysAsyncTask(double lat, double lon, DailyAdapter mAdapter, RecyclerView recyclerView, doComplete5Days finish) {
        this.lat = lat;
        this.lon = lon;
        this.mAdapter = mAdapter;
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
