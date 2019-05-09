package com.project.mobile.weatherapp.utils;

import com.project.mobile.weatherapp.R;

public class WeatherIcon {
    public static int getIconId(String code){
        switch (code) {
            case "01d":
                return R.drawable.img_clearsky_day;
            case "02d":
                return R.drawable.img_fewclouds_day;
            case "03d":
                return R.drawable.img_scatteredclouds;
            case "04d":
                return R.drawable.img_brokenclouds;
            case "04n":
                return R.drawable.img_brokenclouds;
            case "09d":
                return R.drawable.img_showerrain;
            case "10d":
                return R.drawable.img_rain_day;
            case "11d":
                return R.drawable.img_thunderstorm;
            case "13d":
                return R.drawable.img_snow;
            case "50d":
                return R.drawable.img_mist;
            case "01n":
                return R.drawable.img_clearsky_night;
            case "02n":
                return R.drawable.img_fewclouds_night;
            case "03n":
                return R.drawable.img_scatteredclouds;
            case "09n" :
                return R.drawable.img_showerrain;
            case "10n":
                return R.drawable.img_rain_night;
            case "11n":
                return R.drawable.img_thunderstorm;
            case "13n":
                return R.drawable.img_snow;
            case "50n":
                return R.drawable.img_mist;
        }
        return 0;
    }
}
