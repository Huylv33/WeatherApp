package com.project.mobile.weatherapp.model;

public class Daily {
    private String mTextDate;
    private String mTextWeather;
    private String mTempMax;
    private String mTempMin;
    public Daily() {

    }
    public  Daily (String mTextDate, String mTextWeather, String mTempMax, String mTempMin) {
        this.mTextDate = mTextDate;
        this.mTextWeather = mTextWeather;
        this.mTempMax = mTempMax;
        this.mTempMin = mTempMin;
    }

    public  String getmTextDate() {
        return mTextDate;
    }

    public  void setmTextDate(String mTextDate) {
        this.mTextDate = mTextDate;
    }

    public  String getmTextWeather() {
        return mTextWeather;
    }

    public  void setmTextWeather(String mTextWeather) {
        this.mTextWeather = mTextWeather;
    }

    public  String getmTempMax() {
        return mTempMax;
    }

    public  void setmTempMax(String mTempMax) {
        this.mTempMax = mTempMax;
    }

    public  String getmTempMin() {
        return mTempMin;
    }

    public  void setmTempMin(String mTempMin) {
        this.mTempMin = mTempMin;
    }
}
