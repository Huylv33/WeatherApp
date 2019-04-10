package com.project.mobile.weatherapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ProgressBar;

public class WeatherAsyncTask extends AsyncTask {

    private  String q;
    private double lat, lon;
    Activity activity;

    public WeatherAsyncTask(String q, Activity activity) {
        this.q = q;
        this.activity = activity;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
