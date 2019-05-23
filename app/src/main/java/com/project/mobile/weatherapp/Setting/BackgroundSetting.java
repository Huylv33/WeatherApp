package com.project.mobile.weatherapp.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class BackgroundSetting {
    public Activity activity;
    public String backgroundId;

    public BackgroundSetting(Activity activity){
        this.activity = activity;
    }

    public void loadBackgroundSetting() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("BackgroundSetting", Context.MODE_PRIVATE);
        if(sharedPreferences != null) {
            this.backgroundId = sharedPreferences.getString("BackgroundId", null);
        }
    }

    public void saveBackgroundSetting() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("BackgroundSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("BackgroundSetting", this.backgroundId);
        editor.apply();

    }


}
