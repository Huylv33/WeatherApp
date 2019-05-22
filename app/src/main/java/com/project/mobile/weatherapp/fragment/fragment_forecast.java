package com.project.mobile.weatherapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mobile.weatherapp.adapter.DailyAdapter;
import com.project.mobile.weatherapp.R;
import com.project.mobile.weatherapp.model.Daily;
import com.project.mobile.weatherapp.model.open_weather_map.ListOfWeather;
import com.project.mobile.weatherapp.model.open_weather_map.OpenWeatherPredict;
import com.project.mobile.weatherapp.utils.NetworkAndGPSChecking;
import com.project.mobile.weatherapp.utils.Weather5DaysAsyncTask;
import com.project.mobile.weatherapp.utils.doComplete5Days;

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
    public List<Daily> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DailyAdapter mAdapter;
    private DataCommunication dataCommunication;
    private Context context;
    private Weather5DaysAsyncTask weather5DaysAsyncTask;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_daily);

        weather5DaysAsyncTask = new Weather5DaysAsyncTask(lat, lon,mAdapter,recyclerView, new doComplete5Days() {
            @Override
            public void doComplete(OpenWeatherPredict openWeatherPredict) {

                for (ListOfWeather list : openWeatherPredict.getListWeather()) {
                    Log.i("Content", list.getDt_txt());
                    Log.i("Content", list.getTemp_max() + "");
                    Log.i("Content", list.getTemp_min() + "");
                    Daily daily = new Daily();
                    daily.setmTextWeather(list.getWeather().get(0).getDescription());
                    daily.setmTextDate(list.getDt_txt());
                    daily.setmTempMin(list.getTemp_min() + "");
                    daily.setmTempMax(list.getTemp_max() + "");
                    mList.add(daily);
                }
                mAdapter = new DailyAdapter(mList,getContext());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                Log.i("mList size in here ", mList.size() + "");

            }
        });
        if (NetworkAndGPSChecking.isNetworkAvailable(context) && NetworkAndGPSChecking.isGPSAvailable(context)) {
            weather5DaysAsyncTask.execute();
        }
        return view;
    }
    private void initView () {

    }

    public List<Daily> getmList() {
        return mList;
    }

}