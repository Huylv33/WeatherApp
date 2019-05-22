package com.project.mobile.weatherapp.utils;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.project.mobile.weatherapp.adapter.DailyAdapter;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherHours;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherPredict;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WeatherHoursAsyncTask extends AsyncTask<Void,Void, OpenWeatherHours> {

    private NumberFormat format = new DecimalFormat("#0.0");
    public DailyAdapter mAdapter;
    public RecyclerView recyclerView;

    private  String q;
    private double lat, lon;
    public doCompleteHours finish = null;

    private TypePrediction typePrediction;

    public WeatherHoursAsyncTask(String q,DailyAdapter mAdapter,RecyclerView recyclerView, doCompleteHours finish) {
        this.q = q;
        this.finish = finish;
        this.mAdapter = mAdapter;
        typePrediction = TypePrediction.ADDRESS_NAME;
    }
    public WeatherHoursAsyncTask(double lat, double lon, DailyAdapter mAdapter, RecyclerView recyclerView, doCompleteHours finish) {
        this.lat = lat;
        this.lon = lon;
        this.mAdapter = mAdapter;
        this.finish = finish;
        typePrediction = TypePrediction.LATITUDE_LONGITUDE;
    }

    @Override
    protected OpenWeatherHours doInBackground(Void... params) {
        OpenWeatherHours openWeatherHours = null;
        if (typePrediction == TypePrediction.ADDRESS_NAME){
            openWeatherHours = WeatherMapApi.getWeatherHours(this.q);
        }
        else if (typePrediction == TypePrediction.LATITUDE_LONGITUDE){
            openWeatherHours = WeatherMapApi.getWeatherHours(this.lat,this.lon);
        }
        return openWeatherHours;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(OpenWeatherHours openWeatherHours) {
        super.onPostExecute(openWeatherHours);
        finish.doComplete(openWeatherHours);
    }
}
