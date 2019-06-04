package com.project.mobile.weatherapp.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.project.mobile.weatherapp.HourlyAdapter;
import com.project.mobile.weatherapp.MainActivity;
import com.project.mobile.weatherapp.ManagerNotificationActivity;
import com.project.mobile.weatherapp.Setting.LocationSetting;
import com.project.mobile.weatherapp.model.Hourly;
import com.project.mobile.weatherapp.model.airvisual.Location;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherHours;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.model.open_weather_map.Weather;
import com.project.mobile.weatherapp.utils.Constants;

import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.utils.GPSTracker;
import com.project.mobile.weatherapp.utils.NetworkAndGPSChecking;
import com.project.mobile.weatherapp.utils.TimeAndDateConverter;
import com.project.mobile.weatherapp.utils.WeatherAsyncTask;
import com.project.mobile.weatherapp.utils.WeatherHoursAsyncTask;
import com.project.mobile.weatherapp.utils.WeatherIcon;
import com.project.mobile.weatherapp.utils.doComplete;
import com.project.mobile.weatherapp.utils.doCompleteHours;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Created by framgia on 23/05/2017.
 */
public class SchedulingService extends IntentService {
    private static final int TIME_VIBRATE = 1000;

    public WeatherAsyncTask weatherAsyncTask;
    public LocationSetting locationSetting;
    public GPSTracker gpsTracker;

    public SchedulingService() {
        super(SchedulingService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        locationSetting = new LocationSetting(this);
        locationSetting.loadLocationSetting();
        gpsTracker = new GPSTracker(this);

        int index = intent.getIntExtra(Constants.KEY_TYPE, 0);

        if(locationSetting.usingLocation) {
            weatherAsyncTask = new WeatherAsyncTask(gpsTracker.getLatitude(), gpsTracker.getLongitude(), new doComplete() {
                @Override
                public void doComplete(OpenWeatherMap openWeatherMap) {
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    String channelId = "some_channel_id";
                    CharSequence channelName = "Some Channel";
                    int importance = NotificationManager.IMPORTANCE_LOW;
                    NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                    notificationManager.createNotificationChannel(notificationChannel);
                    Intent notificationIntent = new Intent(gpsTracker.mContext, ManagerNotificationActivity.class);
                    notificationIntent
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    int requestID = (int) System.currentTimeMillis();
                    PendingIntent contentIntent = PendingIntent
                            .getActivity(gpsTracker.mContext, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(gpsTracker.mContext)
                                    .setSmallIcon(WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon()))
                                    .setContentTitle(getString(R.string.app_name))
                                    .setContentText("index = " + index)
                                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                    .setDefaults(Notification.DEFAULT_SOUND)
                                    .setAutoCancel(true)
                                    .setPriority(6)
                                    .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE,
                                            TIME_VIBRATE})
                                    .setContentIntent(contentIntent)
                                    .setChannelId(channelId);

                    notificationManager.notify(index, builder.build());
                }
            });
            weatherAsyncTask.execute();
        }
        else {
            weatherAsyncTask = new WeatherAsyncTask(locationSetting.city, new doComplete() {
                @Override
                public void doComplete(OpenWeatherMap openWeatherMap) {
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    String channelId = "some_channel_id";
                    CharSequence channelName = "Some Channel";
                    int importance = NotificationManager.IMPORTANCE_LOW;
                    NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                    notificationManager.createNotificationChannel(notificationChannel);
                    Intent notificationIntent = new Intent(gpsTracker.mContext, ManagerNotificationActivity.class);
                    notificationIntent
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    int requestID = (int) System.currentTimeMillis();
                    PendingIntent contentIntent = PendingIntent
                            .getActivity(gpsTracker.mContext, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(gpsTracker.mContext)
                                    .setSmallIcon(WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon()))
                                    .setContentTitle(getString(R.string.app_name))
                                    .setContentText("index = " + index)
                                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                    .setDefaults(Notification.DEFAULT_SOUND)
                                    .setAutoCancel(true)
                                    .setPriority(6)
                                    .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE,
                                            TIME_VIBRATE})
                                    .setContentIntent(contentIntent)
                                    .setChannelId(channelId);

                    notificationManager.notify(index, builder.build());
                }
            });
            weatherAsyncTask.execute();
        }
    }



}
