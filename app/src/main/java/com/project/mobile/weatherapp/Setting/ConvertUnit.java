package com.project.mobile.weatherapp.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import com.project.mobile.weatherapp.R;

public class ConvertUnit {
    public Boolean usingCelcius;
    public float rainRatio;
    public float timeRatio;

    public Activity activity;

    public ConvertUnit(Activity activity) {
        this.activity = activity;
    }

    public void loadConvertUnit() {
        ToggleSwitch toggleSwitchTemp = (ToggleSwitch) activity.findViewById(R.id.switch_temperature);
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ConvertUnit", Context.MODE_PRIVATE);
        if(sharedPreferences != null) {
            this.usingCelcius = sharedPreferences.getBoolean("usingCelcius", true);
            this.rainRatio = sharedPreferences.getFloat("RainRatio", 1);
            this.timeRatio = sharedPreferences.getFloat("TimeRatio", 1);
        }
    }

    public void saveConvertUnit() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ConvertUnit", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("usingCelcius", this.usingCelcius);
        editor.putFloat("RainRatio", this.rainRatio);
        editor.putFloat("TimeRatio", this.timeRatio);
        editor.apply();
    }
}
