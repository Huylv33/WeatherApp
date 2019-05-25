package com.project.mobile.weatherapp.Setting;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

public class LocationSetting {
    public float lat;
    public float lon;
    public String city;
    public String country;
    public Boolean usingLocation;
    public Activity activity;

    public LocationSetting(Activity activity) {
        this.activity = activity;
    }

    public void loadLocationSetting() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LocationSetting", Context.MODE_PRIVATE);
        if(sharedPreferences != null) {
            this.lat = sharedPreferences.getFloat("Lat", 0);
            this.lon = sharedPreferences.getFloat("Lon", 0);
            this.city = sharedPreferences.getString("City", "Hanoi");
            this.country = sharedPreferences.getString("Country", "Vietnam");
            this.usingLocation = sharedPreferences.getBoolean("usingLocation", true);
            Log.i("using location", this.usingLocation.toString());
            Log.i("load ", this.city);
        }

    }

    public void saveLocationSetting() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LocationSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("Lon",  (float) this.lon);
        editor.putFloat("Lat", (float) this.lat);
        editor.putString("City", this.city);
        editor.putBoolean("usingLocation", this.usingLocation);
        editor.putString("Country", this.country);
        editor.apply();
        Log.i("luu ", this.city);
    }
}
