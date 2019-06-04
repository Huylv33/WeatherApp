package com.project.mobile.weatherapp.utils;

import com.project.mobile.weatherapp.R;

public class ConvertId2WallpaperId {
    public int convertId2Id(int id){
        switch (id){
            case 0:
                return R.drawable.wallpaper5;
            case 1:
                return R.drawable.wallpaper6;
            case 2:
                return R.drawable.wallpaper7;
            case 3:
                return R.drawable.wallpaper9;
            case 4:
                return R.drawable.wallpaper10;
            case 5:
                return R.drawable.wallpaper4;
            case 6:
                return R.drawable.wallpaper12;
            case 7:
                return R.drawable.wallpaper13;
        }
        return 0;
    }
}
