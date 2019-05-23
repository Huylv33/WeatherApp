package com.project.mobile.weatherapp.utils;

import android.os.AsyncTask;

import com.project.mobile.weatherapp.model.airvisual.AirVisual;

public class AirVisualAsyncTask extends AsyncTask<Void,Void, AirVisual> {
    private double lat, lon;
    private String city, state, country;
    private doCompleteAirVisual finish;
    public AirVisualAsyncTask(double lat, double lon, doCompleteAirVisual finish) {
        this.lat = lat;
        this.lon = lon;
        this.finish = finish;
    }
    @Override
    protected AirVisual doInBackground(Void... voids) {
        AirVisual airVisual = AirVisualApi.getNearestCityData(lat,lon);
        return airVisual;
    }
    @Override
    protected void onPostExecute(AirVisual airVisual) {
        super.onPostExecute(airVisual);
        finish.doComplete(airVisual);
    }
}
