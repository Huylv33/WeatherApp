package com.project.mobile.weatherapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.project.mobile.weatherapp.broadcast.Broadcast;
import com.project.mobile.weatherapp.PermissionAboveMarshmellow;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.setting.ConvertUnitSetting;
import com.project.mobile.weatherapp.setting.PrepareDaySetting;
import com.project.mobile.weatherapp.model.airvisual.AirVisual;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.utils.AirVisualAsyncTask;
import com.project.mobile.weatherapp.utils.ConvertUnit;
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
public class FragmentToday extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  Context context;
    private WeatherAsyncTask weatherAsyncTask;
    private AirVisualAsyncTask airVisualAsyncTask;
    private double lat;
    private double lon;
    public Boolean usingLocation;
    public String city;
    public String country;
    public ConvertUnitSetting convertUnitSetting;
    public ConvertUnit convertUnit;
    private Broadcast broadcast;
    public PrepareDaySetting prepareDaySetting;
    public TextView detailView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        Bundle args = getArguments();
        lat = args.getDouble("lat");
        lon = args.getDouble("lon");
        usingLocation = args.getBoolean("usingLocation");
        city = args.getString("city");
        country = args.getString("country");
        convertUnitSetting = new ConvertUnitSetting(context);
        convertUnitSetting.loadConvertUnit();
        convertUnit = new ConvertUnit(convertUnitSetting.usingCelcius, convertUnitSetting.velocity);
        prepareDaySetting = new PrepareDaySetting(context);
        prepareDaySetting.loadPrepareDaySetting();
    }

    private void displayCurrentWeatherView(OpenWeatherMap openWeatherMap) {
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
        String tempName = "°C";
        if (openWeatherMap.getCod() != 200) {
            SharedPreferences sharedPref = context.getSharedPreferences("current_weather_data", Context.MODE_PRIVATE);
            String openWeatherMapJson = sharedPref.getString("current_weather","");
            openWeatherMap = new Gson().fromJson(openWeatherMapJson,OpenWeatherMap.class);
        }
        if(convertUnitSetting.usingCelcius == 0) {
            convertUnit.convert(openWeatherMap);
        }
        else{
            convertUnit.convert(openWeatherMap);
            tempName = "°F";
        }
        String velocityDegree = " m/s";
        if(convertUnitSetting.velocity == 1) {
            convertUnit.convertVelocity(openWeatherMap);
            velocityDegree = " km/h";
        }
        String detail = "";
        if(prepareDaySetting.umbrella){
            if(openWeatherMap.getMain().getHumidity() > prepareDaySetting.umbrella_seek)
                detail = detail + "Nhớ mang ô nhé :* ! ";
        }
        if(prepareDaySetting.highTemp){
            if(openWeatherMap.getMain().getTemp_max() > prepareDaySetting.highTemp_seek){
                if(detail != null)
                    detail += "Trời cũng nóng lắm, nhớ uống đủ nước nhé ! ";
                else
                    detail += "Trời nóng lắm, nhớ mặc áo dài tay khi ra ngoài nhé !";
            }
        }
        if(prepareDaySetting.coat){
            if(openWeatherMap.getMain().getTemp() < prepareDaySetting.coat_seek){
                detail += "Trời lạnh rồi, nhớ mặc ấm kẻo ốm nhé ! ";
            }
        }
        detailView = (TextView) getActivity().findViewById(R.id.ngaycuaban);
        detailView.setText(detail);
        detailView.setTextColor(Color.WHITE);

        imgWeather.setImageResource(WeatherIcon.getIconId(openWeatherMap.getWeather().get(0).getIcon()));

        String temperature= (int)(openWeatherMap.getMain().getTemp())+ tempName;
        String minTemp= format.format(openWeatherMap.getMain().getTemp_min())+ tempName;
        String maxTemp= format.format(openWeatherMap.getMain().getTemp_max()) + tempName;
        long timezone = openWeatherMap.getTimezone() / 3600;

        String TimeZone;
        if(timezone > 0)
            TimeZone = "GMT+" + timezone;
        else if(timezone < 0)
            TimeZone = "GMT" + timezone;
        else
            TimeZone = "GMT";
        txtSunrise.setText(TimeAndDateConverter.getTime
                (openWeatherMap.getSys().getSunrise(), TimeZone, convertUnitSetting.using12h));
        txtSunset.setText(TimeAndDateConverter.getTime
                (openWeatherMap.getSys().getSunset(), TimeZone, convertUnitSetting.using12h));

        txtCurrentAddressName.setText(openWeatherMap.getName());
        txtTemperature.setText(temperature);
        txtMinTemp.setText(minTemp);
        txtMaxTemp.setText(maxTemp);
        String wind= format.format(openWeatherMap.getWind().getSpeed())+ velocityDegree;
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
        loadWeatherInfo();
        broadcast = new Broadcast() {
            @Override
            public void onReceive(Context context, Intent intent) {
                convertUnitSetting.loadConvertUnit();
                convertUnit = new ConvertUnit(convertUnitSetting.usingCelcius, convertUnitSetting.velocity);
                loadWeatherInfo();
            }
        };

        IntentFilter filter = new IntentFilter("setting.unit");
        context.registerReceiver(broadcast, filter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgWeather = (ImageView) getActivity().findViewById(R.id.imgWeatherToday);
        if (imgWeather == null) {

        }

    }

    private void loadWeatherInfo() {
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
                        if (openWeatherMap != null) {
                            displayCurrentWeatherView(openWeatherMap);
                            saveLocalWeatherLocalData(openWeatherMap);
                        }

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
                                //KHONG NHAN DUOC DU LIEU CHO NOI NAY
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
        context.unregisterReceiver(broadcast);
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