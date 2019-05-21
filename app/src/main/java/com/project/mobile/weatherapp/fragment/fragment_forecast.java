package com.project.mobile.weatherapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mobile.weatherapp.adapter.DailyAdapter;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.model.Daily;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherMap;
import com.project.mobile.weatherapp.utils.NetworkChecking;
import com.project.mobile.weatherapp.utils.WeatherAsyncTask;
import com.project.mobile.weatherapp.utils.doComplete;

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
    private List<Daily> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DailyAdapter mAdapter;
    private DataCommunication dataCommunication;
    private Context context;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        Bundle args = getArguments();
        lat = args.getDouble("lat");
        lon = args.getDouble("lon");
    }
    private void getDailyWeather(){
        if (NetworkChecking.isNetworkAvailable(getContext())) {
            WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(lat, lon, new doComplete() {
                @Override
                public void doComplete(OpenWeatherMap openWeatherMap) {

                }
            });
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, null);
        initView();
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_daily);
        mAdapter = new DailyAdapter(mList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        return view;
    }
    private void initView () {
        Daily daily = new Daily("THỨ NĂM, THG 5 16", "Nắng nhẹ", " 36", "26");
        mList.add(daily);
    }

}