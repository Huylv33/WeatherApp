package com.project.mobile.weatherapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.mobile.weatherapp.broadcast.Broadcast;
import com.project.mobile.weatherapp.adapter.HourlyAdapter;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.setting.ConvertUnitSetting;
import com.project.mobile.weatherapp.model.Hourly;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherHours;
import com.project.mobile.weatherapp.utils.ConvertUnit;
import com.project.mobile.weatherapp.utils.NetworkAndGPSChecking;
import com.project.mobile.weatherapp.utils.TimeAndDateConverter;
import com.project.mobile.weatherapp.utils.WeatherHoursAsyncTask;
import com.project.mobile.weatherapp.utils.doCompleteHours;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


/**

 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public class FragmentHourly extends Fragment {
    private double lat;
    private double lon;
    public Boolean usingLocation;
    public String city;
    public String country;
    private Context context;

    private List<Hourly> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HourlyAdapter mAdapter;
    public WeatherHoursAsyncTask weatherHoursAsyncTask;
    public ConvertUnitSetting convertUnitSetting;
    public ConvertUnit convertUnit;
    private BroadcastReceiver broadcast;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Override
    public void onCreate(Bundle savedInstanceState){
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
    }

    public void update() {
        mList.clear();
        if (NetworkAndGPSChecking.isNetworkAvailable(context) && NetworkAndGPSChecking.isGPSAvailable(context)) {
            if(usingLocation) {
                weatherHoursAsyncTask = new WeatherHoursAsyncTask(lat, lon, mAdapter,recyclerView, new doCompleteHours() {
                    @Override
                    public void doComplete(OpenWeatherHours openWeatherHours) {
                        NumberFormat format = new DecimalFormat("#0.0");
                        String tempName = "°C";
                        if(convertUnitSetting.usingCelcius != 0) {
                            tempName = "°F";
                        }
                        convertUnit.convert(openWeatherHours);
                        String velocityDegree = " m/s";
                        if(convertUnitSetting.velocity == 1) {
                            convertUnit.convertVelocity(openWeatherHours);
                            velocityDegree = " km/h";
                        }
                        for (com.project.mobile.weatherapp.model.open_weather_map.List list : openWeatherHours.list) {
                            Hourly hourly = new Hourly();
                            hourly.list = list;
                            hourly.setmTextHumidity(list.getMain().getHumidity() + "%");
                            hourly.setmTextTemp(format.format(list.getMain().getTemp())+ tempName);
                            hourly.setmTextTime(TimeAndDateConverter.Convert12h(list.getDt_txt(), convertUnitSetting.using12h));
                            hourly.setmTextWind(format.format(list.getWind().getSpeed()) + velocityDegree);
                            hourly.setWeatherIcon(list.getWeather().get(0).getIcon());
                            mList.add(hourly);
                        }
                        String openWeatherHoursJson = new Gson().toJson(openWeatherHours);
                        Log.d("hourly",openWeatherHoursJson);
                        SharedPreferences sharedPref = getActivity().getSharedPreferences
                                ("hourly_weather_data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("hourly_weather",openWeatherHoursJson);
                        editor.apply();
                        mAdapter = new HourlyAdapter(mList,getContext());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);

                    }
                });
                weatherHoursAsyncTask.execute();
            }
            else {
                weatherHoursAsyncTask = new WeatherHoursAsyncTask(city, mAdapter,recyclerView, new doCompleteHours() {
                    @Override
                    public void doComplete(OpenWeatherHours openWeatherHours) {
                        NumberFormat format = new DecimalFormat("#0.0");
                        String tempName = "°C";
                        if(convertUnitSetting.usingCelcius != 0) {
                            tempName = "°F";
                        }
                        convertUnit.convert(openWeatherHours);
                        String velocityDegree = " m/s";
                        if(convertUnitSetting.velocity == 1) {
                            convertUnit.convertVelocity(openWeatherHours);
                            velocityDegree = " km/h";
                        }

                        for (com.project.mobile.weatherapp.model.open_weather_map.List list : openWeatherHours.list) {
                            Hourly hourly = new Hourly();
                            hourly.list = list;
                            hourly.setmTextHumidity(list.getMain().getHumidity() + "%");
                            hourly.setmTextTemp(format.format(list.getMain().getTemp())+ tempName);
                            hourly.setmTextTime(TimeAndDateConverter.Convert12h(list.getDt_txt(), convertUnitSetting.using12h));
                            hourly.setmTextWind(format.format(list.getWind().getSpeed()) + velocityDegree);
                            hourly.setWeatherIcon(list.getWeather().get(0).getIcon());
                            //hourly.setmTextWeather(list.getWeather().get(0).getDescription());
                            //hourly.setmTextPressure(list.getMain().getPressure() + " hpa");
                            mList.add(hourly);
                        }
                        String openWeatherHoursJson = new Gson().toJson(openWeatherHours);
                        Log.d("hourly",openWeatherHoursJson);
                        SharedPreferences sharedPref = getActivity().getSharedPreferences
                                ("hourly_weather_data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("hourly_weather",openWeatherHoursJson);
                        editor.apply();
                        mAdapter = new HourlyAdapter(mList,getContext());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);
                        Log.i("mList size in here ", mList.size() + "");

                    }
                });
                weatherHoursAsyncTask.execute();
            }
        }
        else useLocalData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hourly, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_hourly);

        update();
        broadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                convertUnitSetting.loadConvertUnit();
                convertUnit = new ConvertUnit(convertUnitSetting.usingCelcius, convertUnitSetting.velocity);
                update();
            }
        };

        IntentFilter filter = new IntentFilter("setting.unit");
        context.registerReceiver(broadcast, filter);
        return view;
    }

    private void useLocalData() {
        NumberFormat format = new DecimalFormat("#0.0");

        SharedPreferences sharedPref = getActivity().getSharedPreferences
                ("hourly_weather_data", Context.MODE_PRIVATE);
        if (sharedPref != null) {
            String openWeatherHoursJson = sharedPref.getString("hourly_weather","");
            openWeatherHoursJson = openWeatherHoursJson.substring(8,openWeatherHoursJson.length() - 1);
            Type listType = new TypeToken<List<com.project.mobile.weatherapp.model.open_weather_map.List>>(){}.getType();
            List<com.project.mobile.weatherapp.model.open_weather_map.List> lists = new Gson().fromJson(openWeatherHoursJson, listType);
            for (com.project.mobile.weatherapp.model.open_weather_map.List list : lists) {
                Hourly hourly = new Hourly();
                hourly.setmTextHumidity(list.getMain().getHumidity() + "%");
                hourly.setmTextTemp(format.format(list.getMain().getTemp() - 273.15)+ "°C");
                hourly.setmTextTime(TimeAndDateConverter.Convert12h(list.getDt_txt(), convertUnitSetting.using12h));
                hourly.setmTextWind(list.getWind().getSpeed() + " m/s");
                hourly.setWeatherIcon(list.getWeather().get(0).getIcon());
                mList.add(hourly);
            }
            mAdapter = new HourlyAdapter(mList,getContext());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(mAdapter);
        }
    }
}