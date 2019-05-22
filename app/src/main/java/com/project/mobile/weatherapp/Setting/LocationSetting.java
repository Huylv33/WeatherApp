package com.project.mobile.weatherapp.Setting;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

public class LocationSetting {
    public Location location;
    public String placeName;
    public Activity activity;

    public LocationSetting(Activity activity) {
        this.activity = activity;
    }

    public void loadLocationSetting() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LocationSetting", Context.MODE_PRIVATE);
        if(sharedPreferences != null) {
            this.location.setLongitude(sharedPreferences.getFloat("Lon", 0));
            this.location.setLatitude(sharedPreferences.getFloat("Lat", 0));
            this.placeName = sharedPreferences.getString("PlaceName", "Hanoi");

        }
    }

    public void saveLocationSetting() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("LocationSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("Lon",  (float) this.location.getLongitude());
        editor.putFloat("Lat", (float) this.location.getLatitude());
        editor.putString("PlaceName", this.placeName);
    }


}
