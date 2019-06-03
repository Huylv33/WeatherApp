package com.project.mobile.weatherapp.Broadcast;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.project.mobile.weatherapp.MainActivity;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.Setting.LocationSetting;
import com.project.mobile.weatherapp.Setting.NotificationSetting;
import com.project.mobile.weatherapp.Setting.PrepareDaySetting;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.utils.WeatherAsyncTask;
import com.project.mobile.weatherapp.utils.doComplete;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Noti extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 3;
    public LocationSetting locationSetting;

    public NotificationSetting notificationSetting;
    public OpenWeatherMap openWeatherMap1;
    public PrepareDaySetting prepareDaySetting;

    public Noti() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        NumberFormat format = new DecimalFormat("#0.0");
        Log.i("demo", "30");




//        locationSetting = new LocationSetting(context);
//        notificationSetting = new NotificationSetting(context);
//        prepareDaySetting = new PrepareDaySetting(context);
//        locationSetting.loadLocationSetting();
//        notificationSetting.loadNotificationSetting();
//
//        if(locationSetting.usingLocation) {
//            Log.i("demo", "300");
//            WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(locationSetting.lat, locationSetting.lon, new doComplete() {
//                @Override
//                public void doComplete(OpenWeatherMap openWeatherMap) {
//                    openWeatherMap1 = openWeatherMap;
//                    Calendar now = Calendar.getInstance();
//                    DateFormat format1 = SimpleDateFormat.getTimeInstance();
//                    Toast.makeText(context,openWeatherMap1.toString(), Toast.LENGTH_LONG).show();
//                    NotificationManager notificationManager = (NotificationManager) context
//                            .getSystemService(Context.NOTIFICATION_SERVICE);
//                    long when = System.currentTimeMillis();
//
//
//                    Intent notificationIntent = new Intent(context, MainActivity.class);
//                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                    NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
//                            context).setSmallIcon(R.drawable.img_clearsky_night)
//                            .setContentTitle("Weather Notification")
//                            .setContentText(openWeatherMap.getWeather().get(0).getDescription()).setSound(alarmSound)
//                            .setAutoCancel(true).setWhen(when)
//                            .setContentIntent(pendingIntent)
//                            .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//                    notificationManager.notify(100, mNotifyBuilder.build());
//                    //Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        }
//        else {
//            WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(locationSetting.city, new doComplete() {
//                @Override
//                public void doComplete(OpenWeatherMap openWeatherMap) {
//                    openWeatherMap1 = openWeatherMap;
//                    Calendar now = Calendar.getInstance();
//                    DateFormat format1 = SimpleDateFormat.getTimeInstance();
//                    Toast.makeText(context,openWeatherMap1.toString(), Toast.LENGTH_LONG).show();
//                    NotificationManager notificationManager = (NotificationManager) context
//                            .getSystemService(Context.NOTIFICATION_SERVICE);
//                    long when = System.currentTimeMillis();
//
//
//                    Intent notificationIntent = new Intent(context, MainActivity.class);
//                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                            notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                    NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
//                            context).setSmallIcon(R.drawable.img_brokenclouds)
//                            .setContentTitle("Weather Notification")
//                            .setContentText(openWeatherMap.getWeather().get(0).getDescription()).setSound(alarmSound)
//                            .setAutoCancel(true).setWhen(when)
//                            .setContentIntent(pendingIntent)
//                            .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//                    notificationManager.notify(100, mNotifyBuilder.build());
//                    //Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }
}