package com.project.mobile.weatherapp.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ConvertUnit {
    public float tempRatio;
    public float rainRatio;
    public float timeRatio;

    public Activity activity;

    public ConvertUnit(Activity activity) {
        this.activity = activity;
    }

    public void loadConvertUnit() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ConvertUnit", Context.MODE_PRIVATE);
        if(sharedPreferences != null) {
            this.tempRatio = sharedPreferences.getFloat("TempRatio", 1);
            this.rainRatio = sharedPreferences.getFloat("RainRatio", 1);
            this.timeRatio = sharedPreferences.getFloat("TimeRatio", 1);
        }
    }

    public void saveConvertUnit() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ConvertUnit", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("TempRatio", this.tempRatio);
        editor.putFloat("RainRatio", this.rainRatio);
        editor.putFloat("TimeRatio", this.timeRatio);
        editor.apply();

    }
}
