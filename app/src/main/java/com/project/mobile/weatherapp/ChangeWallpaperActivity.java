package com.project.mobile.weatherapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.mobile.weatherapp.adapter.BackgroundAdapter;
import com.project.mobile.weatherapp.model.Hourly;

import java.util.ArrayList;
import java.util.List;

public class ChangeWallpaperActivity extends AppCompatActivity {

    private java.util.List<Integer> mId = new ArrayList<Integer>();
    private RecyclerView recyclerView;
    private BackgroundAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_wallpaper);

        recyclerView = (RecyclerView) findViewById(R.id.rv_wallpaper);
        mId.add(R.drawable.wallpaper5);
        mId.add(R.drawable.wallpaper6);
        mId.add(R.drawable.wallpaper7);
        mId.add(R.drawable.wallpaper9);
        mId.add(R.drawable.wallpaper10);
        mId.add(R.drawable.wallpaper4);
        mId.add(R.drawable.wallpaper12);
        mId.add(R.drawable.wallpaper13);

        mAdapter = new BackgroundAdapter(mId,this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


    }
}
