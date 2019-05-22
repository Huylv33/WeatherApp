package com.project.mobile.weatherapp.Setting;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

public class LocationSetting {
    public float lat;
    public float lon;
    public String city;
    public String country;
    public Boolean usingLocation = true;
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
            this.city = sharedPreferences.getString("Country", "Vietnam");
            this.usingLocation = sharedPreferences.getBoolean("usingLocation", true);
        }

    }

    public void saveLocationSetting() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LocationSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("Lon",  (float) this.lon);
        editor.putFloat("Lat", (float) this.lat);
        editor.putString("City", this.city);
        editor.putString("Country", this.country);
        editor.putBoolean("usingLocation", this.usingLocation);
    }


}