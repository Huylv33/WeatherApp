package com.project.mobile.weatherapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.google.gson.Gson;
import com.project.mobile.weatherapp.model.open_weather_map.Main;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.utils.NetworkAndGPSChecking;
import com.project.mobile.weatherapp.utils.WeatherAsyncTask;
import com.project.mobile.weatherapp.utils.WeatherIcon;
import com.project.mobile.weatherapp.utils.doComplete;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private static WeatherAsyncTask weatherAsyncTask;
    private  Context context;
    private static double lat;
    private static double lon;
    private static RemoteViews views;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        String temp_name = "°C";
        String timeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
        //Tạo remote view
        //NumberFormat format = new DecimalFormat("#0.0");

        views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        // Khai báo tạm string để test layout
        //String temperature= "25°C";
        //String weather= "Bầu trời quang đãng";
        //views.setTextViewText(R.id.text_temperature,temperature);
        //views.setImageViewResource(R.id.icon_weather, R.drawable.img_fewclouds_day);
        //views.setTextViewText(R.id.text_weather,weather);
        SharedPreferences prefs = context.getSharedPreferences("current_weather_data",Context.MODE_PRIVATE);
        if (prefs != null) {
            String openWeatherMapJson = prefs.getString("current_weather", "");
            Log.d("json2", openWeatherMapJson);
            OpenWeatherMap openWeatherMap = new Gson().fromJson(openWeatherMapJson, OpenWeatherMap.class);
            Log.d("json3", openWeatherMap.getSys().getSunrise() + "");
            NumberFormat format = new DecimalFormat("#0.0");
            //ImageView imgWeather = (ImageView) getActivity().findViewById(R.id.imgWeather);
            Main main = openWeatherMap.getMain();
            String mesg = openWeatherMap.getWeather().get(0).getDescription();
            String temp = (int) (main.getTemp()) + "°";
            String name = openWeatherMap.getName();
            views.setTextViewText(R.id.text_location_widget, name);
            views.setTextViewText(R.id.text_weather_widget, mesg);
            views.setTextViewText(R.id.text_temperature_widget, temp);
            views.setImageViewResource(R.id.icon_weather_widget, WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon()));
        }



        // Ấn vào widget khởi chạy màn hình chính
        Intent launchMain = new Intent(context, MainActivity.class);
        PendingIntent pendingMainIntent = PendingIntent.getActivity(context, 0, launchMain, 0);
        views.setOnClickPendingIntent(R.id.layout_widget, pendingMainIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void getWeather() {

    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

