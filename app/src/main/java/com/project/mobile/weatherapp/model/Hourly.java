package com.project.mobile.weatherapp.model;

public class Hourly {
    private String mTextTime;
    private String mTextTemp;
    private String mTextWind;
    private String mTextHumidity;
    public  Hourly (String mTextTime, String mTextTemp, String mTextWind, String mTextHumidity) {
        this.mTextTime = mTextTime;
        this.mTextTemp = mTextTemp;
        this.mTextWind = mTextWind;
        this.mTextHumidity = mTextHumidity;
    }

    public  String getmTextTime() {
        return mTextTime;
    }

    public  void setmTextTime(String mTextTime) {
        this.mTextTime = mTextTime;
    }

    public  String getmTextTemp() {
        return mTextTemp;
    }

    public  void setmTextTemp(String mTextTemp) {
        this.mTextTemp = mTextTemp;
    }

    public  String getmTextWind() {
        return mTextWind;
    }

    public  void setmTextWind(String mTextWind) {
        this.mTextWind = mTextWind;
    }

    public  String getmTextHumidity() {
        return mTextHumidity;
    }

    public  void setmTextHumidity(String mTextHumidity) {
        this.mTextHumidity = mTextHumidity;
    }
}

