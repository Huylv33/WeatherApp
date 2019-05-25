package com.project.mobile.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;

public class UnitSettingActivity extends AppCompatActivity {
    private int tempUnit;
    private int timeUnit;
    private int distanceUnit;
    private int precipitationUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_setting);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ToggleSwitch toggleSwitchTemp = (ToggleSwitch) findViewById(R.id.switch_temperature);
        ToggleSwitch toggleSwitchTime = (ToggleSwitch) findViewById(R.id.switch_time_format);
        ToggleSwitch toggleSwitchDistance = (ToggleSwitch) findViewById(R.id.switch_distance);
        ToggleSwitch toggleSwitchPre = (ToggleSwitch) findViewById(R.id.switch_precipitation);
        SharedPreferences sharedPreferences = getSharedPreferences("ConvertUnit",MODE_PRIVATE);
        tempUnit = sharedPreferences.getInt("temp_unit",0);
        timeUnit = sharedPreferences.getInt("time_unit",0);
        distanceUnit = sharedPreferences.getInt("distance_unit",0);
        precipitationUnit = sharedPreferences.getInt(   "precipitation_unit",0);
        toggleSwitchTime.setCheckedPosition(tempUnit);
        toggleSwitchTemp.setCheckedPosition(timeUnit);
        toggleSwitchDistance.setCheckedPosition(distanceUnit);
        toggleSwitchPre.setCheckedPosition(precipitationUnit);
        toggleSwitchTemp.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                tempUnit = i;
                SharedPreferences sharedPreferences =  getSharedPreferences
                        ("ConvertUnit", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.apply();
            }
        });
    }
}
