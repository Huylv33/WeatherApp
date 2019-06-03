package com.project.mobile.weatherapp.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeAndDateConverter {
    public  static String getDate(long dateInSeconds){
        Date date = new Date(dateInSeconds * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        String dateString = dateFormat.format(date);
        Log.e("getDate : ", dateString);
        return dateString;
    }

    public static String getTime(long timeInSeconds, String timezone){
        Date time = new Date(timeInSeconds * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT+7"));
        String timeString = dateFormat.format(time);
        Log.e("getTime : ", timeString);

        return timeString;
    }

    public static String getDay(long timeInSeconds){
        // current date
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(currentDate);
        // api date
        Date day = new Date(timeInSeconds * 1000);
        SimpleDateFormat datFormat = new SimpleDateFormat("EEEE");
        String dayNameString = datFormat.format(day);

        if (currentDate.equals(day)){
            return "Today";
        }

        return dayNameString;
    }
}
