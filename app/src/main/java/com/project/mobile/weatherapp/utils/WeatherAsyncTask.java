package com.project.mobile.weatherapp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.model.OpenWeatherMap;


import java.lang.ref.WeakReference;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WeatherAsyncTask extends AsyncTask<Void,Void,OpenWeatherMap> {
    private NumberFormat format = new DecimalFormat("#0.0");
    private  String q;
    private double lat, lon;
    private WeakReference<Activity> activity;

    private TypePrediction typePrediction;

    public WeatherAsyncTask(String q, WeakReference<Activity> activity) {
        this.q = q;
        this.activity = activity;
        typePrediction = TypePrediction.ADDRESS_NAME;
    }
    public WeatherAsyncTask(double lat, double lon, Activity activity) {
        this.lat = lat;
        this.lon = lon;
        this.activity = new WeakReference<>(activity);
        typePrediction = TypePrediction.LATITUDE_LONGITUDE;
    }
    @Override
    protected OpenWeatherMap doInBackground(Void... params) {
        OpenWeatherMap openWeatherMap = null;
        if (typePrediction == TypePrediction.ADDRESS_NAME){
            openWeatherMap = WeatherMapApi.getWeatherInfor(this.q);
        }
        else if (typePrediction == TypePrediction.LATITUDE_LONGITUDE){
            openWeatherMap = WeatherMapApi.getWeatherInfor(this.lat,this.lon);
        }
        return openWeatherMap;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(OpenWeatherMap openWeatherMap) {
        super.onPostExecute(openWeatherMap);
        String json = new Gson().toJson(openWeatherMap);
        Log.d("check",json);
        Activity activity = this.activity.get();
        ImageView imgWeather = (ImageView) activity.findViewById(R.id.imgWeather);
        TextView txtTemperature=(TextView) activity.findViewById(R.id.txtTemperature);
        TextView txtCurrentAddressName=(TextView) activity.findViewById(R.id.txtCurrentAddressName);
        TextView txtMaxTemp=(TextView) activity.findViewById(R.id.txtMaxTemp);
        TextView txtMinTemp=(TextView) activity.findViewById(R.id.txtMinTemp);
        TextView txtWind=(TextView) activity.findViewById(R.id.txtWind);
        TextView txtCloudliness= (TextView) activity.findViewById(R.id.txtCloudliness);
        TextView txtPressure= (TextView) activity.findViewById(R.id.txtPressure);
        TextView txtHumidty= (TextView) activity.findViewById(R.id.txtHumidty);
        TextView txtSunrise= (TextView) activity.findViewById(R.id.txtSunrise);
        TextView txtSunset= (TextView) activity.findViewById(R.id.txtSunset);
        imgWeather.setImageResource(WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon()));
        String temperature= format.format(openWeatherMap.getMain().getTemp()-273.15)+"°C";
        String minTemp= format.format(openWeatherMap.getMain().getTemp_min()-273.15)+"°C";
        String maxTemp= format.format(openWeatherMap.getMain().getTemp_max()-273.15)+"°C";
        txtSunrise.setText(TimeAndDateConverter.getTime(openWeatherMap.getSys().getSunrise()));
        txtSunset.setText(TimeAndDateConverter.getTime(openWeatherMap.getSys().getSunset()));
        txtCurrentAddressName.setText(openWeatherMap.getName());
        txtTemperature.setText(temperature);
        txtMinTemp.setText(minTemp);
        txtMaxTemp.setText(maxTemp);
        String wind= openWeatherMap.getWind().getSpeed()+" m/s";
        String mesg = openWeatherMap.getWeather().get(0).getMain();
        String cloudiness= mesg;
        String pressure= openWeatherMap.getMain().getPressure()+" hpa";
        String humidity=openWeatherMap.getMain().getHumidity()+" %";
        txtWind.setText(wind);
        txtCloudliness.setText(cloudiness);
        txtPressure.setText(pressure);
        txtHumidty.setText(humidity);
    }

}
