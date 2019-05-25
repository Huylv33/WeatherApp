package com.project.mobile.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;

public class UnitSettingActivity extends AppCompatActivity {
    String tempUnit = "C";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_setting);
        ToggleSwitch toggleSwitchTemp = (ToggleSwitch) findViewById(R.id.switch_temperature);
        ToggleSwitch toggleSwitchTime = (ToggleSwitch) findViewById(R.id.switch_time_format);
        ToggleSwitch toggleSwitchDistance = (ToggleSwitch) findViewById(R.id.switch_distance);
        ToggleSwitch toggleSwitchPre = (ToggleSwitch) findViewById(R.id.switch_precipitation);

        toggleSwitchTime.setCheckedPosition(0);
        toggleSwitchTemp.setCheckedPosition(0);
        toggleSwitchDistance.setCheckedPosition(0);
        toggleSwitchPre.setCheckedPosition(0);
        toggleSwitchTemp.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                if (i == 0) {
                    tempUnit = "C";
                } else tempUnit = "F";
                SharedPreferences sharedPreferences =  getSharedPreferences
                        ("ConvertUnit", Context.MODE_PRIVATE);
                Log.d("open",tempUnit);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("unit_temp",tempUnit);
                editor.apply();
            }
        });
    }
}
