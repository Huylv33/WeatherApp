package com.project.mobile.weatherapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.project.mobile.weatherapp.service.NotiService;
import com.project.mobile.weatherapp.service.SchedulingServ;

public class AlarmUtils {
    private static int INDEX = 1;
    public AlarmManager alarmManager;
    public Calendar cal;
    PendingIntent pendingIntent;
    PendingIntent pendingIntent1;

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public  AlarmUtils(Context context) {
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SchedulingServ.class);
        Intent intent1 = new Intent(context, NotiService.class);
        intent1.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("HH:mm", Locale.getDefault());
        intent.putExtra(Constants.KEY_TYPE, INDEX);
        pendingIntent = PendingIntent.getService(context, INDEX, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent1 = PendingIntent.getService(context, INDEX, intent1, 0);
    }

    public void start(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("HH:mm", Locale.getDefault());
        long time;
        if(cal.getTimeInMillis() - calendar.getTimeInMillis() < 0)
            time = 24 * 60 *60 * 1000 + cal.getTimeInMillis();
        else
            time = cal.getTimeInMillis();
        Log.i("calen", time + "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            alarmManager
                    .setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        } else {
            alarmManager
                    .set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
    }

    public void stop(){
        if (this.alarmManager != null)
            this.alarmManager.cancel(pendingIntent);
    }

    public  void stopRe(){
        if(this.alarmManager != null) {
            this.alarmManager.cancel(pendingIntent1);
        }
    }
    public void startRepeat() {
        Log.i("repeat", "1");
        Calendar calendar = Calendar.getInstance();
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 30 * 1000 , pendingIntent1 );
    }

}
