package com.project.mobile.weatherapp.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.MainThread;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.project.mobile.weatherapp.MainActivity;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.setting.ConvertUnitSetting;
import com.project.mobile.weatherapp.setting.LocationSetting;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.utils.Constants;
import com.project.mobile.weatherapp.utils.ConvertUnit;
import com.project.mobile.weatherapp.utils.GPSTracker;
import com.project.mobile.weatherapp.utils.TimeAndDateConverter;
import com.project.mobile.weatherapp.utils.WeatherAsyncTask;
import com.project.mobile.weatherapp.utils.WeatherIcon;
import com.project.mobile.weatherapp.utils.doComplete;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NotiService extends IntentService {
    private static final int TIME_VIBRATE = 1000;
    public WeatherAsyncTask weatherAsyncTask;
    public LocationSetting locationSetting;
    public GPSTracker gpsTracker;
    public ConvertUnitSetting convertUnitSetting;
    public ConvertUnit convertUnit;

    public NotiService() {
        super(SchedulingServ.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        locationSetting = new LocationSetting(this);
        locationSetting.loadLocationSetting();
        gpsTracker = new GPSTracker(this);
        convertUnitSetting = new ConvertUnitSetting(this);
        convertUnitSetting.loadConvertUnit();
        convertUnit = new ConvertUnit(convertUnitSetting.usingCelcius, convertUnitSetting.velocity);
        convertUnit.using12h = convertUnitSetting.using12h;

        int index = intent.getIntExtra(Constants.KEY_TYPE, 0);
        NumberFormat format = new DecimalFormat("#0.0");
        if (locationSetting.usingLocation) {
            weatherAsyncTask = new WeatherAsyncTask(gpsTracker.getLatitude(), gpsTracker.getLongitude(), new doComplete() {
                @Override
                public void doComplete(OpenWeatherMap openWeatherMap) {
                    int iconId = WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon());
                    String des = openWeatherMap.getWeather().get(0).getDescription();
                    String TimeZone;
                    long timezone = openWeatherMap.getTimezone() / 3600;
                    if (timezone > 0)
                        TimeZone = "GMT+" + timezone;
                    else if(timezone < 0)
                        TimeZone = "GMT" + timezone;
                    else
                        TimeZone = "GMT";
                    String time = TimeAndDateConverter.getTime(openWeatherMap.getSys().getSunrise(), TimeZone, convertUnitSetting.using12h);
                    String tempName = "°C";
                    if(convertUnitSetting.usingCelcius == 0) {
                        convertUnit.convert(openWeatherMap);
                    }
                    else{
                        convertUnit.convert(openWeatherMap);
                        tempName = "°F";
                    }
                    String temp = format.format(openWeatherMap.getMain().getTemp()) + tempName;
                    String location = openWeatherMap.getName();
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent notificationIntent = new Intent(gpsTracker.mContext, MainActivity.class);
                    notificationIntent
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    int requestID = (int) System.currentTimeMillis();
                    PendingIntent contentIntent = PendingIntent
                            .getActivity(gpsTracker.mContext, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    RemoteViews notiLayout = new RemoteViews(getPackageName(), R.layout.weather_notification);
                    notiLayout.setTextViewText(R.id.text_temperature, temp);
                    notiLayout.setTextViewText(R.id.text_weather, des);
                    notiLayout.setImageViewResource(R.id.icon_weather, iconId);
                    notiLayout.setTextViewText(R.id.text_time, time);
                    notiLayout.setTextViewText(R.id.text_location, location);
                    String channelId = "some_channel_id";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence channelName = "Some Channel";
                        int importance = NotificationManager.IMPORTANCE_LOW;
                        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                        notificationManager.createNotificationChannel(notificationChannel);

                    }
                    NotificationCompat.Builder builder =
                            new NotificationCompat.Builder(gpsTracker.mContext, channelId)
                                    .setSmallIcon(WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon()))
                                    .setContentTitle(getString(R.string.app_name))
                                    .setContentText("index = " + index)
                                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                    .setDefaults(Notification.DEFAULT_SOUND)
                                    .setAutoCancel(true)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE,
                                            TIME_VIBRATE})
                                    .setContentIntent(contentIntent)
                                    .setCustomContentView(notiLayout);
                    notificationManager.notify(index,builder.build());
                }
            });
            weatherAsyncTask.execute();
        } else {
            weatherAsyncTask = new WeatherAsyncTask(locationSetting.city, new doComplete() {
                @Override
                public void doComplete(OpenWeatherMap openWeatherMap) {
                    int iconId = WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon());
                    String des = openWeatherMap.getWeather().get(0).getDescription();
                    String TimeZone;
                    long timezone = openWeatherMap.getTimezone() / 3600;
                    if(timezone > 0)
                        TimeZone = "GMT+" + timezone;
                    else if(timezone < 0)
                        TimeZone = "GMT" + timezone;
                    else
                        TimeZone = "GMT";
                    String time = TimeAndDateConverter.getTime(openWeatherMap.getSys().getSunrise(), TimeZone, convertUnitSetting.using12h);
                    String tempName = "°C";
                    if(convertUnitSetting.usingCelcius == 0) {
                        convertUnit.convert(openWeatherMap);
                    }
                    else{
                        convertUnit.convert(openWeatherMap);
                        tempName = "°F";
                    }
                    String temp = format.format(openWeatherMap.getMain().getTemp()) + tempName;
                    String location = openWeatherMap.getName();

                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    RemoteViews notiLayout = new RemoteViews(getPackageName(), R.layout.weather_notification);
                    notiLayout.setTextViewText(R.id.text_temperature, temp);
                    notiLayout.setTextViewText(R.id.text_weather, des);
                    notiLayout.setImageViewResource(R.id.icon_weather, iconId);
                    notiLayout.setTextViewText(R.id.text_time, time);
                    notiLayout.setTextViewText(R.id.text_location, location);
                    String channelId = "some_channel_id";

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence channelName = "Some Channel";
                        int importance = NotificationManager.IMPORTANCE_LOW;
                        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                        notificationManager.createNotificationChannel(notificationChannel);
                        Intent notificationIntent = new Intent(gpsTracker.mContext, MainActivity.class);
                        notificationIntent
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        int requestID = (int) System.currentTimeMillis();
                        PendingIntent contentIntent = PendingIntent
                                .getActivity(gpsTracker.mContext, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(gpsTracker.mContext,channelId)
                                        .setSmallIcon(WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon()))
                                        .setContentTitle(getString(R.string.app_name))
                                        .setContentText("index = " + index)
                                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                        .setDefaults(Notification.DEFAULT_SOUND)
                                        .setAutoCancel(true)
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE,
                                                TIME_VIBRATE})
                                        .setContentIntent(contentIntent)
                                        .setCustomContentView(notiLayout);

                        notificationManager.notify(index, builder.build());
                    }

                }
            });
            weatherAsyncTask.execute();
        }
    }
}
