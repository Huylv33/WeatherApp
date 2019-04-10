package com.project.mobile.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.mobile.weatherapp.utils.WeatherAsyncTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);
        String q = "London";
        WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(q,this);
        weatherAsyncTask.execute();
    }


}
