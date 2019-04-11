package com.project.mobile.weatherapp.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.model.OpenWeatherMap;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class WeatherAsyncTask extends AsyncTask {
    NumberFormat format = new DecimalFormat("#0.0");
    private  String q;
    private double lat, lon;
    Activity activity;
    TypePrediction typePrediction;

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
        Log.d("Here", Double.toString(openWeatherMap.getCoord().getLat()));
        return openWeatherMap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        CharSequence text = "Hello toast!";
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(activity,text,Toast.LENGTH_SHORT);
//        toast.show();
    }

    @Override
    protected void onPostExecute(Object openWeatherJson) {
        super.onPostExecute(openWeatherJson);
        OpenWeatherMap openWeatherMap = (OpenWeatherMap)openWeatherJson;
        String json = new Gson().toJson(openWeatherMap);
        Log.d("check",json);
        TextView txtTemperature=(TextView) activity.findViewById(R.id.txtTemperature);
        TextView txtCurrentAddressName=(TextView) activity.findViewById(R.id.txtCurrentAddressName);
//        ImageView imageView=(ImageView) activity.findViewById(R.id.imgBauTroi);
        TextView txtMaxTemp=(TextView) activity.findViewById(R.id.txtMaxTemp);
        TextView txtMinTemp=(TextView) activity.findViewById(R.id.txtMinTemp);
        TextView txtWind=(TextView) activity.findViewById(R.id.txtWind);
        TextView txtCloudliness= (TextView) activity.findViewById(R.id.txtCloudliness);
        TextView txtPressure= (TextView) activity.findViewById(R.id.txtPressure);
        TextView txtHumidty= (TextView) activity.findViewById(R.id.txtHumidty);
        TextView txtSunrise= (TextView) activity.findViewById(R.id.txtSunrise);
        TextView txtSunset= (TextView) activity.findViewById(R.id.txtSunset);
        String temperature= format.format(openWeatherMap.getMain().getTemp()-273.15)+"°C";
        String minTemp= format.format(openWeatherMap.getMain().getTemp_min()-273.15)+"°C";
        String maxTemp= format.format(openWeatherMap.getMain().getTemp_max()-273.15)+"°C";
        txtCurrentAddressName.setText(openWeatherMap.getName());
        txtTemperature.setText(temperature);
        txtMinTemp.setText(minTemp);
        txtMaxTemp.setText(maxTemp);
        String wind= openWeatherMap.getWind().getSpeed()+" m/s";
        String mesg = openWeatherMap.getWeather().get(0).getMain();
        String cloudiness=mesg;
        String pressure= openWeatherMap.getMain().getPressure()+" hpa";
        String humidity=openWeatherMap.getMain().getHumidity()+" %";
        txtWind.setText(wind);
        txtCloudliness.setText(cloudiness);
        txtPressure.setText(pressure);
        txtHumidty.setText(humidity);
    }


    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);

    }

}
