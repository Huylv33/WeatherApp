package com.project.mobile.weatherapp.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class NotificationSetting {
    public boolean notification;
    public boolean prepareDaily;
    public String timePrepareDaily;
    public boolean vibrate; // rung
    public boolean arlarm; // am thanh bao

    public Context context;

    public NotificationSetting(Context context) {
        this.context = context;
    }

    public void loadNotificationSetting() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("NotificationSetting", Context.MODE_PRIVATE);
        if(sharedPreferences != null) {
            this.notification = sharedPreferences.getBoolean("Notification", false);
            this.prepareDaily = sharedPreferences.getBoolean("PrepareDaily", false);
            this.timePrepareDaily = sharedPreferences.getString("TimePrepareDaily", "8");
            this.vibrate = sharedPreferences.getBoolean("Vibrate", false);
            this.arlarm = sharedPreferences.getBoolean("Alarm", false);
        }
    }
    public void saveNotificationSetting() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("NotificationSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Notification", this.notification);
        editor.putBoolean("PrepareDaily", this.prepareDaily);
        editor.putString("TimePrepareDaily", this.timePrepareDaily);
        editor.putBoolean("Vibrate", this.vibrate);
        editor.putBoolean("Alarm", this.arlarm);
        editor.apply();
    }
}

