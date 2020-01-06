package com.project.mobile.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import com.project.mobile.weatherapp.setting.BackgroundSetting;
import com.project.mobile.weatherapp.setting.ConvertUnitSetting;

public class UnitSettingActivity extends AppCompatActivity {
    private ConvertUnitSetting convertUnitSetting;
    public RelativeLayout relativeLayout;
    public BackgroundSetting backgroundSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_setting);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        backgroundSetting = new BackgroundSetting(this);
        backgroundSetting.loadBackgroundSetting();
        relativeLayout = findViewById(R.id.activity_unit_setting);
        relativeLayout.setBackgroundResource(backgroundSetting.backgroundId);
        ToggleSwitch temp = this.findViewById(R.id.switch_temperature);
        ToggleSwitch distance = this.findViewById(R.id.switch_distance);
        ToggleSwitch time_format = this.findViewById(R.id.switch_time_format);
        ToggleSwitch pre = this.findViewById(R.id.switch_precipitation);
        convertUnitSetting = new ConvertUnitSetting(this);
        convertUnitSetting.loadConvertUnit();
        temp.setCheckedPosition(convertUnitSetting.usingCelcius);
        distance.setCheckedPosition(convertUnitSetting.velocity);
        time_format.setCheckedPosition(convertUnitSetting.using12h);
        pre.setCheckedPosition(convertUnitSetting.usingmm);
        time_format.setOnChangeListener(position -> {
            convertUnitSetting.using12h = position;
            convertUnitSetting.saveConvertUnit();
            Intent intent = new Intent();
            intent.setAction("setting.unit");
            sendBroadcast(intent);
        });

        distance.setOnChangeListener(i -> {
            convertUnitSetting.velocity = i;
            convertUnitSetting.saveConvertUnit();
            Intent intent = new Intent();
            intent.setAction("setting.unit");
            sendBroadcast(intent);
        });

        pre.setOnChangeListener(i -> {
            convertUnitSetting.usingmm = i;
            convertUnitSetting.saveConvertUnit();
        });

        temp.setOnChangeListener(i -> {
            convertUnitSetting.usingCelcius = i;
            convertUnitSetting.saveConvertUnit();
            Intent intent = new Intent();
            intent.setAction("setting.unit");
            sendBroadcast(intent);
        });
    }

}
