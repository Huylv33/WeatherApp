package com.project.mobile.weatherapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.project.mobile.weatherapp.HourlyAdapter;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.adapter.DailyAdapter;
import com.project.mobile.weatherapp.model.Daily;
import com.project.mobile.weatherapp.model.Hourly;
import com.project.mobile.weatherapp.model.open_weather_map.ListOfWeather;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherHours;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherPredict;
import com.project.mobile.weatherapp.utils.NetworkAndGPSChecking;
import com.project.mobile.weatherapp.utils.Weather5DaysAsyncTask;
import com.project.mobile.weatherapp.utils.WeatherHoursAsyncTask;
import com.project.mobile.weatherapp.utils.doComplete5Days;
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
public class fragment_hourly extends Fragment {

    private double lat;
    private double lon;
    private Context context;

    private List<Hourly> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HourlyAdapter mAdapter;
    public WeatherHoursAsyncTask weatherHoursAsyncTask;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        context = getActivity().getApplicationContext();
        Bundle args = getArguments();
        lat = args.getDouble("lat");
        lon = args.getDouble("lon");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hourly, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_hourly);
        weatherHoursAsyncTask = new WeatherHoursAsyncTask(lat, lon, mAdapter,recyclerView, new doCompleteHours() {
            @Override
                public void doComplete(OpenWeatherHours openWeatherHours) {
                NumberFormat format = new DecimalFormat("#0.0");

                for (com.project.mobile.weatherapp.model.open_weather_map.List list : openWeatherHours.list) {
                    Hourly hourly = new Hourly();
                    hourly.setmTextHumidity(list.getMain().getHumidity() + "%");
                    hourly.setmTextTemp(format.format(list.getMain().getTemp() - 273.15)+ "°C");
                    hourly.setmTextTime(list.getDt_txt());
                    hourly.setmTextWind(list.getWind().getSpeed() + " m/s");
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
                Log.i("mList size in here ", mList.size() + "");
            }
        });
        if (NetworkAndGPSChecking.isNetworkAvailable(context) && NetworkAndGPSChecking.isGPSAvailable(context)) {
            weatherHoursAsyncTask.execute();
        }
        else useLocalData();
        return  view;
    }

    // chi de cho test thu thoi
    private void initView () {
        Hourly hourly = new Hourly("18:00", "35°", " 22 mm", " 87%");
        mList.add(hourly);
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
                hourly.setmTextTime(list.getDt_txt());
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