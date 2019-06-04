package com.project.mobile.weatherapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.mobile.weatherapp.Broadcast.Broadcast;
import com.project.mobile.weatherapp.Setting.ConvertUnitSetting;
import com.project.mobile.weatherapp.adapter.DailyAdapter;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.model.Daily;
import com.project.mobile.weatherapp.model.open_weather_map.ListOfWeather;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherPredict;
import com.project.mobile.weatherapp.utils.ConvertUnit;
import com.project.mobile.weatherapp.utils.NetworkAndGPSChecking;
import com.project.mobile.weatherapp.utils.TimeAndDateConverter;
import com.project.mobile.weatherapp.utils.Weather5DaysAsyncTask;
import com.project.mobile.weatherapp.utils.WeatherAsyncTask;
import com.project.mobile.weatherapp.utils.doComplete;
import com.project.mobile.weatherapp.utils.doComplete5Days;

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
public class fragment_forecast extends Fragment {
    private double lat;
    private double lon;
    public Boolean usingLocation;
    public String city;
    public String country;
    public List<Daily> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DailyAdapter mAdapter;
    private DataCommunication dataCommunication;
    private Context context;
    private Weather5DaysAsyncTask weather5DaysAsyncTask;
    public ConvertUnitSetting convertUnitSetting;
    public ConvertUnit convertUnit;
    private Broadcast mbroadcast;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Override
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
        convertUnit = new ConvertUnit(convertUnitSetting.usingCelcius,convertUnitSetting.velocity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        context.unregisterReceiver(mbroadcast);
    }
    public void updateView(){
        mList.clear();
        if (NetworkAndGPSChecking.isNetworkAvailable(context) && NetworkAndGPSChecking.isGPSAvailable(context)) {
            if(usingLocation){
                weather5DaysAsyncTask = new Weather5DaysAsyncTask(lat, lon, mAdapter, recyclerView, new doComplete5Days() {
                    @Override
                    public void doComplete(OpenWeatherPredict openWeatherPredict) {
                        String tempName = "°C";
                        if(convertUnitSetting.usingCelcius == 0) {
                            convertUnit.convert(openWeatherPredict);
                        }
                        else{
                            convertUnit.convert(openWeatherPredict);
                            tempName = "°F";
                        }
                        Log.i("Lenght", openWeatherPredict.getListWeather().size() + "");
                        NumberFormat format = new DecimalFormat("#0.0");
                        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences
                                ("MyPrefsFile", Context.MODE_PRIVATE);
                        String openWeatherPredictJson = new Gson().toJson(openWeatherPredict);
                        Log.d("open",openWeatherPredictJson);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("forecast_weather",openWeatherPredictJson);
                        editor.apply();
                        for (ListOfWeather list : openWeatherPredict.getListWeather()) {
                            Daily daily = new Daily();
                            daily.setmTextWeather(list.getWeather().get(0).getDescription());
                            daily.setmTextDate(list.getDt_txt().split(" ")[0]);

                            daily.setmTempMin(format.format(list.getTemp_min() ) + tempName);
                            daily.setmTempMax(format.format(list.getTemp_max()) + tempName);
                            daily.setmIconId(list.getWeather().get(0).getIcon());
                            mList.add(daily);
                        }
                        mAdapter = new DailyAdapter(mList,getContext());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);
                        Log.i("mList size in here ", mList.size() + "");
                    }
                });
                weather5DaysAsyncTask.execute();
            }
            else {
                weather5DaysAsyncTask = new Weather5DaysAsyncTask(city, mAdapter, recyclerView, new doComplete5Days() {
                    @Override
                    public void doComplete(OpenWeatherPredict openWeatherPredict) {
                        String tempName = "°C";
                        if(convertUnitSetting.usingCelcius == 0) {
                            convertUnit.convert(openWeatherPredict);
                        }
                        else{
                            convertUnit.convert(openWeatherPredict);
                            tempName = "°F";
                        }
                        NumberFormat format = new DecimalFormat("#0.0");
                        Log.i("Lenght", openWeatherPredict.getListWeather().size() + "");
                        SharedPreferences sharedPreferences =  getActivity().getSharedPreferences
                                ("MyPrefsFile", Context.MODE_PRIVATE);
                        String openWeatherPredictJson = new Gson().toJson(openWeatherPredict);
                        Log.d("open",openWeatherPredictJson);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("forecast_weather",openWeatherPredictJson);
                        editor.apply();
                        for (ListOfWeather list : openWeatherPredict.getListWeather()) {
                            Daily daily = new Daily();
                            daily.setmTextWeather(list.getWeather().get(0).getDescription());
                            daily.setmTextDate(list.getDt_txt().split(" ")[0]);
                            daily.setmTempMin(format.format(list.getTemp_min()) + tempName);
                            daily.setmTempMax(format.format(list.getTemp_max()) + tempName);
                            daily.setmIconId(list.getWeather().get(0).getIcon());
                            mList.add(daily);
                        }
                        mAdapter = new DailyAdapter(mList,getContext());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(mAdapter);
                        Log.i("mList size in here ", mList.size() + "");
                    }
                });
                weather5DaysAsyncTask.execute();
            }

        }
        else useLocalData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_daily);
        updateView();

        mbroadcast = new Broadcast() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("asdas111111d", "11");
                convertUnitSetting.loadConvertUnit();
                convertUnit = new ConvertUnit(convertUnitSetting.usingCelcius, convertUnitSetting.velocity);
                updateView();
            }
        };

        IntentFilter filter = new IntentFilter("setting.unit");
        context.registerReceiver(mbroadcast, filter);

        return view;
    }
    @Override
    public  void onResume(){
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public List<Daily> getmList() {
        return mList;
    }
    private void useLocalData() {
        NumberFormat format = new DecimalFormat("#0.0");
        String s = "{\"listOfWeather\":[{\"clouds\":{\"all\":19},\"dt_txt\":\"2019-05-23 00:00:00\",\"main\":{\"grnd_level\":1000.72,\"humidity\":79,\"pressure\":1010.08,\"sea_level\":1010.08,\"temp\":299.11,\"temp_kf\":-0.12,\"temp_max\":299.229,\"temp_min\":299.11},\"temp_max\":32.940999999999974,\"temp_min\":24.639999999999986,\"weather\":[{\"description\":\"mây thưa\",\"icon\":\"02d\",\"id\":801,\"main\":\"Clouds\"}],\"wind\":{\"deg\":342.208,\"speed\":1.07}},{\"clouds\":{\"all\":72},\"dt_txt\":\"2019-05-24 00:00:00\",\"main\":{\"grnd_level\":999.59,\"humidity\":89,\"pressure\":1009.11,\"sea_level\":1009.11,\"temp\":299.479,\"temp_kf\":0.0,\"temp_max\":299.479,\"temp_min\":299.479},\"temp_max\":33.911,\"temp_min\":25.069999999999993,\"weather\":[{\"description\":\"mây cụm\",\"icon\":\"04d\",\"id\":803,\"main\":\"Clouds\"}],\"wind\":{\"deg\":83.317,\"speed\":1.01}},{\"clouds\":{\"all\":98},\"dt_txt\":\"2019-05-25 00:00:00\",\"main\":{\"grnd_level\":996.19,\"humidity\":86,\"pressure\":1006.07,\"sea_level\":1006.07,\"temp\":300.788,\"temp_kf\":0.0,\"temp_max\":300.788,\"temp_min\":300.788},\"temp_max\":36.488,\"temp_min\":25.069999999999993,\"weather\":[{\"description\":\"mưa vừa\",\"icon\":\"10d\",\"id\":501,\"main\":\"Rain\"}],\"wind\":{\"deg\":216.349,\"speed\":1.1}},{\"clouds\":{\"all\":100},\"dt_txt\":\"2019-05-26 00:00:00\",\"main\":{\"grnd_level\":995.71,\"humidity\":89,\"pressure\":1005.52,\"sea_level\":1005.52,\"temp\":300.324,\"temp_kf\":0.0,\"temp_max\":300.324,\"temp_min\":300.324},\"temp_max\":33.69999999999999,\"temp_min\":25.069999999999993,\"weather\":[{\"description\":\"mưa nhẹ\",\"icon\":\"10d\",\"id\":500,\"main\":\"Rain\"}],\"wind\":{\"deg\":278.315,\"speed\":0.66}},{\"clouds\":{\"all\":100},\"dt_txt\":\"2019-05-27 00:00:00\",\"main\":{\"grnd_level\":997.38,\"humidity\":88,\"pressure\":1006.83,\"sea_level\":1006.83,\"temp\":300.2,\"temp_kf\":0.0,\"temp_max\":300.2,\"temp_min\":300.2},\"temp_max\":33.30000000000001,\"temp_min\":25.069999999999993,\"weather\":[{\"description\":\"mây đen u ám\",\"icon\":\"04d\",\"id\":804,\"main\":\"Clouds\"}],\"wind\":{\"deg\":31.112,\"speed\":1.73}}]}"
       ; SharedPreferences sharedPreferences =  context.getSharedPreferences
                ("MyPrefsFile", Context.MODE_PRIVATE);
        if (sharedPreferences != null) {


            String openWeatherPredictJson = sharedPreferences.getString("forecast_weather","");
            openWeatherPredictJson = openWeatherPredictJson.substring(17,openWeatherPredictJson.length() - 1);

//            openWeatherPredictJson  = "[{\"clouds\":{\"all\":19},\"dt_txt\":\"2019-05-23 00:00:00\",\"main\":{\"grnd_level\":1000.72,\"humidity\":79,\"pressure\":1010.08,\"sea_level\":1010.08,\"temp\":299.11,\"temp_kf\":-0.12,\"temp_max\":299.229,\"temp_min\":299.11},\"temp_max\":32.940999999999974,\"temp_min\":24.639999999999986,\"weather\":[{\"description\":\"mây thưa\",\"icon\":\"02d\",\"id\":801,\"main\":\"Clouds\"}],\"wind\":{\"deg\":342.208,\"speed\":1.07}},{\"clouds\":{\"all\":72},\"dt_txt\":\"2019-05-24 00:00:00\",\"main\":{\"grnd_level\":999.59,\"humidity\":89,\"pressure\":1009.11,\"sea_level\":1009.11,\"temp\":299.479,\"temp_kf\":0.0,\"temp_max\":299.479,\"temp_min\":299.479},\"temp_max\":33.911,\"temp_min\":25.069999999999993,\"weather\":[{\"description\":\"mây cụm\",\"icon\":\"04d\",\"id\":803,\"main\":\"Clouds\"}],\"wind\":{\"deg\":83.317,\"speed\":1.01}},{\"clouds\":{\"all\":98},\"dt_txt\":\"2019-05-25 00:00:00\",\"main\":{\"grnd_level\":996.19,\"humidity\":86,\"pressure\":1006.07,\"sea_level\":1006.07,\"temp\":300.788,\"temp_kf\":0.0,\"temp_max\":300.788,\"temp_min\":300.788},\"temp_max\":36.488,\"temp_min\":25.069999999999993,\"weather\":[{\"description\":\"mưa vừa\",\"icon\":\"10d\",\"id\":501,\"main\":\"Rain\"}],\"wind\":{\"deg\":216.349,\"speed\":1.1}},{\"clouds\":{\"all\":100},\"dt_txt\":\"2019-05-26 00:00:00\",\"main\":{\"grnd_level\":995.71,\"humidity\":89,\"pressure\":1005.52,\"sea_level\":1005.52,\"temp\":300.324,\"temp_kf\":0.0,\"temp_max\":300.324,\"temp_min\":300.324},\"temp_max\":33.69999999999999,\"temp_min\":25.069999999999993,\"weather\":[{\"description\":\"mưa nhẹ\",\"icon\":\"10d\",\"id\":500,\"main\":\"Rain\"}],\"wind\":{\"deg\":278.315,\"speed\":0.66}},{\"clouds\":{\"all\":100},\"dt_txt\":\"2019-05-27 00:00:00\",\"main\":{\"grnd_level\":997.38,\"humidity\":88,\"pressure\":1006.83,\"sea_level\":1006.83,\"temp\":300.2,\"temp_kf\":0.0,\"temp_max\":300.2,\"temp_min\":300.2},\"temp_max\":33.30000000000001,\"temp_min\":25.069999999999993,\"weather\":[{\"description\":\"mây đen u ám\",\"icon\":\"04d\",\"id\":804,\"main\":\"Clouds\"}],\"wind\":{\"deg\":31.112,\"speed\":1.73}}]";
            Type listType = new TypeToken<List<ListOfWeather>>(){}.getType();
            List<ListOfWeather> listOfWeatherList = new Gson().fromJson(openWeatherPredictJson, listType);

            for (ListOfWeather list : listOfWeatherList) {
                Daily daily = new Daily();
                daily.setmTextWeather(list.getWeather().get(0).getDescription());
                daily.setmTextDate(TimeAndDateConverter.Convert12h(list.getDt_txt(), convertUnitSetting.using12h));
                daily.setmTempMin(format.format(list.getTemp_min() -273.15) + "°C");
                daily.setmTempMax(format.format(list.getTemp_max() -273.15) + "°C");
                daily.setmIconId(list.getWeather().get(0).getIcon());
                mList.add(daily);
            }
            mAdapter = new DailyAdapter(mList,getContext());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(mAdapter);
        }
    }
}