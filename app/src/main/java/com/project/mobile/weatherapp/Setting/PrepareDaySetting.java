package com.project.mobile.weatherapp.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PrepareDaySetting {
    public Boolean umbbrela, coat, highTemp, coldTemp;
    public String NoticText;
    public Context context;

    public PrepareDaySetting(Context context) {
        this.context = context;
    }

    public void loadPrepareDaySetting() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PrepareDay", Context.MODE_PRIVATE);
        this.umbbrela = sharedPreferences.getBoolean("umbbrela", false);
        this.coat  = sharedPreferences.getBoolean("coat", false);
        this.highTemp = sharedPreferences.getBoolean("highTemp", false);
        this.coldTemp = sharedPreferences.getBoolean("coldTemp", false);
        Log.i("Umbbrela ", sharedPreferences.getBoolean("umbbrela", false) + "");
        this.NoticText = sharedPreferences.getString("noticText", null);
    }

    public void savePrepareDaySetting() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PrepareDay", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("noticText", this.NoticText);
        editor.putBoolean("umbbrela",this.umbbrela);
        editor.putBoolean("coat", this.coat);
        editor.putBoolean("highTemp", this.highTemp);
        editor.putBoolean("coldTemp", this.coldTemp);
        editor.apply();
    }
}
