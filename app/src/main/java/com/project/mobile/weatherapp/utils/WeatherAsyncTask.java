package com.project.mobile.weatherapp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.Toast;

import com.project.mobile.weatherapp.model.OpenWeatherMap;


public class WeatherAsyncTask extends AsyncTask {

    private  String q;
    private double lat, lon;
    Activity activity;

    public WeatherAsyncTask(String q, Activity activity) {
        this.q = q;
        this.activity = activity;
    }

    @Override
    protected OpenWeatherMap doInBackground(Object[] objects) {
        OpenWeatherMap openWeatherMap = null;
        openWeatherMap = WeatherMapApi.prediction(this.q);
//        try {
//            String idIcon = openWeatherMap.getWeather().get(0).getIcon().toString();
//            String urlIcon = "http://openweathermap.org/img/w/"+idIcon+".png";
////Tiến hành tạo đối tượng URL
//            URL urlConnection = new URL(urlIcon);
////Mở kết nối
//            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
////Đọc dữ liệu
//            InputStream input = connection.getInputStream();
////Tiến hành convert qua hình ảnh
////            myBitmap = BitmapFactory.decodeStream(input);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return openWeatherMap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    protected void onPostExecute(OpenWeatherMap openWeatherMap) {
        super.onPostExecute(openWeatherMap);

        Log.d("Kiemtra",openWeatherMap.toString());
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(activity,text,Toast.LENGTH_SHORT);
        toast.show();
    }
}
