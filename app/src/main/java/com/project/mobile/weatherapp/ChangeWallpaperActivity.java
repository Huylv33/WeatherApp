package com.project.mobile.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mobile.weatherapp.setting.BackgroundSetting;
import com.project.mobile.weatherapp.adapter.BackgroundAdapter;

import java.util.ArrayList;

public class ChangeWallpaperActivity extends AppCompatActivity {

    private java.util.List<Integer> mId = new ArrayList<Integer>();
    private RecyclerView recyclerView;
    private BackgroundAdapter mAdapter;
    public TextView confirm, reject;
    public BackgroundSetting backgroundSetting;
    public RelativeLayout relativeLayout;
    public ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_wallpaper);
        relativeLayout = (RelativeLayout) findViewById(R.id.dialog_change_wallpaper);
        //imageView = (ImageView) findViewById(R.id.iv_background);
        backgroundSetting = new BackgroundSetting(this);
        backgroundSetting.loadBackgroundSetting();
        relativeLayout.setBackgroundResource(backgroundSetting.backgroundId);
        //imageView.setImageResource(backgroundSetting.backgroundId);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

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
        confirm = (TextView) findViewById(R.id.btn_done);
        reject = (TextView) findViewById(R.id.text_no_thank);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent currentLocation = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(currentLocation);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundSetting.backgroundId = convertId2Id(mAdapter.sellectId);
                backgroundSetting.saveBackgroundSetting();
                Intent currentLocation = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(currentLocation);
            }
        });

    }

    public int convertId2Id(int id){
        switch (id){
            case 0:
                return R.drawable.wallpaper5;
            case 1:
                return R.drawable.wallpaper6;
            case 2:
                return R.drawable.wallpaper7;
            case 3:
                return R.drawable.wallpaper9;
            case 4:
                return R.drawable.wallpaper10;
            case 5:
                return R.drawable.wallpaper4;
            case 6:
                return R.drawable.wallpaper12;
            case 7:
                return R.drawable.wallpaper13;
        }
        return 0;
    }

}
