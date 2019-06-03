package com.project.mobile.weatherapp.utils;

import android.util.Log;

import java.text.ParseException;
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

    public static String getTime(long timeInSeconds, String timezone, int using12h){
        Date time = new Date(timeInSeconds * 1000);
        SimpleDateFormat dateFormat;
        if(using12h == 1) {
            dateFormat = new SimpleDateFormat("HH:mm ");
        }
        else {
            dateFormat = new SimpleDateFormat("hh:mm a");
        }
        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone(timezone));
        String timeString = dateFormat.format(time);
        Log.e("getTime : ", timeString);

        return timeString;
    }

    public static String  Convert12h(String time, int using12h) {
        String timeformat = null;
        Date date = null;

        if(using12h == 1) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try{
                date = dateFormat.parse(time);
            }
            catch (Exception e){

            }
            timeformat = outputformat.format(date);
        }
        else{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
            try{
                date = dateFormat.parse(time);
            }
            catch (Exception e){

            }
            timeformat = outputformat.format(date);
//            timeformat = dateFormat.format(date);

        }
        return timeformat;
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
