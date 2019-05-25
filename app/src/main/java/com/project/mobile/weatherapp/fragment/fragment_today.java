package com.project.mobile.weatherapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.github.lzyzsd.circleprogress.ArcProgress;

import com.project.mobile.weatherapp.PermissionAboveMarshmellow;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.model.airvisual.AirVisual;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.utils.AirVisualAsyncTask;
import com.project.mobile.weatherapp.utils.NetworkAndGPSChecking;
import com.project.mobile.weatherapp.utils.TimeAndDateConverter;
import com.project.mobile.weatherapp.utils.WeatherAsyncTask;
import com.project.mobile.weatherapp.utils.WeatherIcon;
import com.project.mobile.weatherapp.utils.doComplete;
import com.project.mobile.weatherapp.utils.doCompleteAirVisual;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**

 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public class fragment_today extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  Context context;
    private WeatherAsyncTask weatherAsyncTask;
    private AirVisualAsyncTask airVisualAsyncTask;
    private double lat;
    private double lon;
    public Boolean usingLocation;
    public String city;
    private String unitTemp;
    public String country;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        Bundle args = getArguments();
        lat = args.getDouble("lat");
        lon = args.getDouble("lon");
        usingLocation = args.getBoolean("usingLocation");
        city = args.getString("city");
        country = args.getString("country");
        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences
                ("ConvertUnit", Context.MODE_PRIVATE);
        unitTemp = sharedPreferences.getString("unit_temp","C");
    }
    private void displayCurrentWeatherView(OpenWeatherMap openWeatherMap){
        NumberFormat format = new DecimalFormat("#0.0");
        ImageView imgWeather = (ImageView) getActivity().findViewById(R.id.imgWeatherToday);
        TextView txtTemperature=(TextView) getActivity().findViewById(R.id.txtTemperature);
        TextView txtCurrentAddressName=(TextView) getActivity().findViewById(R.id.txtCurrentAddressName);
        TextView txtMaxTemp=(TextView) getActivity().findViewById(R.id.txtMaxTemp);
        TextView txtMinTemp=(TextView) getActivity().findViewById(R.id.txtMinTemp);
        TextView txtWind=(TextView) getActivity().findViewById(R.id.txtWind);
        TextView txtCloudliness= (TextView) getActivity().findViewById(R.id.txtCloudliness);
        TextView txtPressure= (TextView) getActivity().findViewById(R.id.txtPressure);
        TextView txtHumidty= (TextView) getActivity().findViewById(R.id.txtHumidty);
        TextView txtSunrise= (TextView) getActivity().findViewById(R.id.txtSunrise);
        TextView txtSunset= (TextView) getActivity().findViewById(R.id.txtSunset);
        imgWeather.setImageResource(WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon()));
        String temperature = "";
        String minTemp = "";
        String maxTemp = "";
        if (unitTemp.equals("C")) {
            temperature= (int) (openWeatherMap.getMain().getTemp()-273.15)+"°C";
            minTemp= format.format(openWeatherMap.getMain().getTemp_min()-273.15)+"°C";
            maxTemp= format.format(openWeatherMap.getMain().getTemp_max()-273.15)+"°C";
        }
        else {
            temperature= (int) (openWeatherMap.getMain().getTemp())+"F";
            minTemp= format.format(openWeatherMap.getMain().getTemp_min())+"F";
            maxTemp= format.format(openWeatherMap.getMain().getTemp_max())+"F";
        }
        txtSunrise.setText(TimeAndDateConverter.getTime(openWeatherMap.getSys().getSunrise()));
        txtSunset.setText(TimeAndDateConverter.getTime(openWeatherMap.getSys().getSunset()));
        txtCurrentAddressName.setText(openWeatherMap.getName());
        txtTemperature.setText(temperature);
        txtMinTemp.setText(minTemp);
        txtMaxTemp.setText(maxTemp);
        String wind= openWeatherMap.getWind().getSpeed()+" m/s";
        String mesg = openWeatherMap.getWeather().get(0).getDescription();
        String cloudiness= mesg;
        String pressure= openWeatherMap.getMain().getPressure()+" hpa";
        String humidity=openWeatherMap.getMain().getHumidity()+" %";
        txtWind.setText(wind);
        txtCloudliness.setText(cloudiness);
        txtPressure.setText(pressure);
        txtHumidty.setText(humidity);
    }
    private void saveLocalWeatherLocalData(OpenWeatherMap openWeatherMap) {
        String openWeatherMapJson = new Gson().toJson(openWeatherMap);
        SharedPreferences sharedPref = getActivity().getSharedPreferences
                ("current_weather_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("current_weather",openWeatherMapJson);
        editor.apply();
    }
    private void displayAirView(int aqius) {
        if (aqius >= 0) {
            ArcProgress arcProgress= (ArcProgress) getActivity().findViewById(R.id.arc_progress);
            arcProgress.setProgress(aqius);
            arcProgress.setMax(300);
            arcProgress.setStrokeWidth(20);
            TextView status = (TextView) getActivity().findViewById(R.id.status);
            if(arcProgress.getProgress() < 50){
                status.setText("GOOD");
                status.setTextColor(Color.argb(255,139,195,74));
                arcProgress.setFinishedStrokeColor(Color.argb(255,139,195,74));
                arcProgress.setTextColor(Color.argb(255,139,195,74));
                arcProgress.setUnfinishedStrokeColor(Color.argb(120,200,200,218));
            }
            if(arcProgress.getProgress() >= 50 && arcProgress.getProgress() < 100){
                status.setText("MODERATE");
                status.setTextColor(Color.argb(255,255,235,59));
                arcProgress.setFinishedStrokeColor(Color.argb(255,255,235,59));
                arcProgress.setTextColor(Color.argb(255,255,235,59));
                arcProgress.setUnfinishedStrokeColor(Color.argb(120,200,200,218));
            }
            if(arcProgress.getProgress() >= 100 && arcProgress.getProgress() < 150){
                status.setText("UNHEALTHY");
                status.setTextColor(Color.argb(255,255,152,0));
                arcProgress.setFinishedStrokeColor(Color.argb(255,255,152,0));
                arcProgress.setTextColor(Color.argb(255,255,152,0));
                arcProgress.setUnfinishedStrokeColor(Color.argb(120,200,200,218));
            }
            if(arcProgress.getProgress() >= 150 && arcProgress.getProgress() < 200){
                status.setText("UNHEALTHY");
                status.setTextColor(Color.argb(255,244,67,54));
                arcProgress.setFinishedStrokeColor(Color.argb(255,244,67,54));
                arcProgress.setTextColor(Color.argb(255,244,67,54));
                arcProgress.setUnfinishedStrokeColor(Color.argb(120,200,200,218));
            }
            if(arcProgress.getProgress() >= 200 && arcProgress.getProgress() < 250){
                status.setText("VERY UNHEALTHY");
                status.setTextColor(Color.parseColor("#27AE60"));
                arcProgress.setFinishedStrokeColor(Color.argb(255,156,39,176));
                arcProgress.setTextColor(Color.argb(255,156,39,176));
                arcProgress.setUnfinishedStrokeColor(Color.argb(120,200,200,218));
            }
            if(arcProgress.getProgress() >= 250 && arcProgress.getProgress() < 300){
                status.setText("HAZARDOUS");
                status.setTextColor(Color.parseColor("#27AE60"));
                arcProgress.setFinishedStrokeColor(Color.argb(255,103,58,183));
                arcProgress.setTextColor(Color.argb(255,103,58,183));
                arcProgress.setUnfinishedStrokeColor(Color.argb(120,200,200,218));
            }
        }
        else {
            //neu aqius < 0 coi la khong hien thi du lieu gi
            ArcProgress arcProgress= (ArcProgress) getActivity().findViewById(R.id.arc_progress);
            arcProgress.setVisibility(View.INVISIBLE);
            TextView status = (TextView) getActivity().findViewById(R.id.status);
            status.setText("Sorry, We don't hava data for this city");
            status.setTextSize(20);
            status.setTextColor(Color.parseColor("#FDFEFE"));
        }
    }
    private void saveLocalAirData(int aqius) {
        SharedPreferences sharedPreferences = context.getSharedPreferences
                ("current_weather_data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("aqius",aqius);
        editor.apply();
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("OnPause","OnPause is called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadWeatherInfor();
        ImageView imgWeather = (ImageView) getActivity().findViewById(R.id.imgWeatherToday);
        if (imgWeather == null) {
            Log.d("fuck","null");
        }
    }

    private void loadWeatherInfor() {
        if (shouldAskPermissions() && !isFirstTimeLauncher()) {
            startActivity(new Intent(getActivity(), PermissionAboveMarshmellow.class));
            getWeather();
        }
        else getWeather();
    }
    private boolean isFirstTimeLauncher() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        boolean is = sharedPreferences.getBoolean("IS_FIRST_LAUNCHER",false);
        return  sharedPreferences.getBoolean("IS_FIRST_LAUNCHER",false);
    }
    private void getWeather() {
        if (NetworkAndGPSChecking.isNetworkAvailable(context) && NetworkAndGPSChecking.isGPSAvailable(context)) {
            if(usingLocation) {
                weatherAsyncTask = new WeatherAsyncTask(lat,lon, new doComplete() {
                    @Override
                    public  void doComplete(OpenWeatherMap openWeatherMap) {
                        displayCurrentWeatherView(openWeatherMap);
                        saveLocalWeatherLocalData(openWeatherMap);
                    }
                });
                weatherAsyncTask.execute();
                airVisualAsyncTask = new AirVisualAsyncTask(lat, lon, new doCompleteAirVisual() {
                    @Override
                    public void doComplete(AirVisual airVisual) {
                        if (airVisual != null) {
                            if (airVisual.getStatus().equals("success")) {
                                int aqius = airVisual.getData().getCurrent().getPollution().getAqius();
                                Log.d("aqius1",aqius + "");
                                displayAirView(aqius);
                                saveLocalAirData(aqius);
                            }
                            else {
                                //KHONG NHAN DUOC DU LIEU CHO NOI NAY -;-
                                displayAirView(-1);
                                saveLocalAirData(-1);
                            }
                        }
                        else {
                            displayAirView(-1);
                            saveLocalAirData(-1);
                        }
                    }
                });
                airVisualAsyncTask.execute();
            }
            else {
                weatherAsyncTask = new WeatherAsyncTask(city, new doComplete() {
                    @Override
                    public  void doComplete(OpenWeatherMap openWeatherMap) {
                        displayCurrentWeatherView(openWeatherMap);
                        saveLocalWeatherLocalData(openWeatherMap);
                    }
                });
                weatherAsyncTask.execute();
                airVisualAsyncTask = new AirVisualAsyncTask(city, city, country, new doCompleteAirVisual() {
                    @Override
                    public void doComplete(AirVisual airVisual) {
                        if (airVisual != null) {
                            if (airVisual.getStatus().equals("success")) {
                                int aqius = airVisual.getData().getCurrent().getPollution().getAqius();
                                displayAirView(aqius);
                                saveLocalAirData(aqius);
                            }
                            else {
                                //KHONG NHAN DUOC DU LIEU CHO NOI NAY -;-
                                displayAirView(-1);
                                saveLocalAirData(-1);
                            }
                        }
                        else {
                            //airvisual nó lại bằng null, lai ra mac dinh
                            displayAirView(-1);
                            saveLocalAirData(-1);
                        }
                    }
                });
                airVisualAsyncTask.execute();
            }

        }
        else useLocalData();
    }
    private boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (weatherAsyncTask != null) {
            weatherAsyncTask.cancel(true);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    private void useLocalData() {
        SharedPreferences sharedPref = context.getSharedPreferences("current_weather_data", Context.MODE_PRIVATE);
        if (sharedPref != null) {
            String openWeatherMapJson = sharedPref.getString("current_weather","");
            OpenWeatherMap openWeatherMap = new Gson().fromJson(openWeatherMapJson, OpenWeatherMap.class);
            displayCurrentWeatherView(openWeatherMap);
            int aqius = sharedPref.getInt("aqius",-1);
            if (aqius > 0){
               displayAirView(aqius);
            }
            else {
                displayAirView(-1);
            }
        }
    }

}
