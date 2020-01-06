package com.project.mobile.weatherapp.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.project.mobile.weatherapp.R;

public class BackgroundSetting {
    public Context activity;
    public int backgroundId;

    public BackgroundSetting(Context activity){
        this.activity = activity;
    }

    public void loadBackgroundSetting() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences
                ("BackgroundSetting", Context.MODE_PRIVATE);
        if(sharedPreferences !=  null) {
            this.backgroundId = sharedPreferences.getInt("Background", R.drawable.wallpaper5);
        }
    }

    public void saveBackgroundSetting() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences
                ("BackgroundSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Background", this.backgroundId);
        editor.apply();
    }
}
