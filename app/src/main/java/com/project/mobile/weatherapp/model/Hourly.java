package com.project.mobile.weatherapp.model;

public class Hourly {
    private String mTextTime;
    private String mTextTemp;
    private String mTextPrecipitation;
    private String mTextHumidity;
    public  Hourly (String mTextTime, String mTextTemp, String mTextPrecipitation, String mTextHumidity) {
        this.mTextTime = mTextTime;
        this.mTextTemp = mTextTemp;
        this.mTextPrecipitation = mTextPrecipitation;
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

    public  String getmTextPrecipitation() {
        return mTextPrecipitation;
    }

    public  void setmTextPrecipitation(String mTextPrecipitation) {
        this.mTextPrecipitation = mTextPrecipitation;
    }

    public  String getmTextHumidity() {
        return mTextHumidity;
    }

    public  void setmTextHumidity(String mTextHumidity) {
        this.mTextHumidity = mTextHumidity;
    }
}

