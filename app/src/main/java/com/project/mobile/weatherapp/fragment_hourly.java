package com.project.mobile.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mobile.weatherapp.model.Hourly;

import java.util.ArrayList;
import java.util.List;


/**

 * Activities that contain this fragment must implement the

 * to handle interaction events.

 * create an instance of this fragment.
 */
public class fragment_hourly extends Fragment {
    private List<Hourly> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HourlyAdapter mAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hourly, null);
        initView();
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_hourly);
        mAdapter = new HourlyAdapter(mList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        return view;
    }
    private void initView () {
        Hourly hourly = new Hourly("18:00", "35°", " 12 mm", " 87%");
        mList.add(hourly);


    }
}