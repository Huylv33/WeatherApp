package com.project.mobile.weatherapp.utils;

import com.project.mobile.weatherapp.R;

public class WeatherIcon {
    public static int getIconId(String code){
        switch (code) {
            case "01d":
                return R.drawable.clear_sky_d;
            case "02d":
                return R.drawable.fewclouds_d;
            case "03d":
                return R.drawable.scatteredclouds;
            case "04d":
                return R.drawable.broke3;
            case "04n":
                return R.drawable.broke3;
            case "09d":
                return R.drawable.shower_rain;
            case "10d":
                return R.drawable.rain_d;
            case "11d":
                return R.drawable.thunder_d;
            case "13d":
                return R.drawable.snow_d;
            case "50d":
                return R.drawable.mist;
            case "01n":
                return R.drawable.clear_sky_n;
            case "02n":
                return R.drawable.fewclouds_n;
            case "03n":
                return R.drawable.scatteredclouds;
            case "09n" :
                return R.drawable.shower_rain;
            case "10n":
                return R.drawable.rain_n;
            case "11n":
                return R.drawable.thunder_n;
            case "13n":
                return R.drawable.snow_n;
            case "50n":
                return R.drawable.mist;
        }
        return 0;
    }
}
