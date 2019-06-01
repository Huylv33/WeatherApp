package com.project.mobile.weatherapp.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class ConvertUnit {
    public int usingCelcius;
    public int usingmm;
    public int using12h;
    public int velocity;

    public  Context context;

    public ConvertUnit(Context activity) {
        this.context = activity;
    }

    public void loadConvertUnit() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ConvertUnit", Context.MODE_PRIVATE);
        if(sharedPreferences != null) {
            this.usingCelcius = sharedPreferences.getInt("usingCelcius", 0);
            this.using12h = sharedPreferences.getInt("using12h", 0);
            this.usingmm = sharedPreferences.getInt("usingmm", 0);
            this.velocity = sharedPreferences.getInt("velocity", 0);
        }
    }

    public void saveConvertUnit() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ConvertUnit", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("usingCelcius", this.usingCelcius);
        editor.putInt("usingmm", this.usingmm);
        editor.putInt("using12h", this.using12h);
        editor.putInt("verlocity", this.velocity);
        editor.apply();

    }
}
