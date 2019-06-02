package com.project.mobile.weatherapp;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import com.project.mobile.weatherapp.Setting.BackgroundSetting;
import com.project.mobile.weatherapp.Setting.ConvertUnitSetting;

public class UnitSettingActivity extends AppCompatActivity {

    private ToggleSwitch temp;
    private ToggleSwitch distance ;
    private  ToggleSwitch time_format;
    private ToggleSwitch pre;
    public ImageView imageView;
    private ConvertUnitSetting convertUnitSetting;
    public RelativeLayout relativeLayout;
    public BackgroundSetting backgroundSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_setting);

        backgroundSetting = new BackgroundSetting(this);
        backgroundSetting.loadBackgroundSetting();
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_unit_setting);
        relativeLayout.setBackgroundResource(backgroundSetting.backgroundId);
//        imageView = (ImageView) findViewById(R.id.activity_unit_setting_imageView);
//        imageView.setBackgroundResource(backgroundSetting.backgroundId);

        ToggleSwitch temp = (ToggleSwitch) this.findViewById(R.id.switch_temperature);
        ToggleSwitch distance = (ToggleSwitch) this.findViewById(R.id.switch_distance);
        ToggleSwitch time_format = (ToggleSwitch) this.findViewById(R.id.switch_time_format);
        ToggleSwitch pre = (ToggleSwitch) this.findViewById(R.id.switch_precipitation);
        convertUnitSetting = new ConvertUnitSetting(this);
        convertUnitSetting.loadConvertUnit();
        temp.setCheckedPosition(convertUnitSetting.usingCelcius);
        distance.setCheckedPosition(convertUnitSetting.velocity);
        time_format.setCheckedPosition(convertUnitSetting.using12h);
        pre.setCheckedPosition(convertUnitSetting.usingmm);


        time_format.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int position) {
                convertUnitSetting.using12h = position;
                convertUnitSetting.saveConvertUnit();

            }
        });

        distance.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                convertUnitSetting.velocity = i;
                convertUnitSetting.saveConvertUnit();
                Intent intent = new Intent();
                intent.setAction("setting.unit");
                sendBroadcast(intent);
                Log.i("AbCdA", i + "");
            }
        });

        pre.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                convertUnitSetting.usingmm = i;
                convertUnitSetting.saveConvertUnit();

            }
        });

        temp.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {

                convertUnitSetting.usingCelcius = i;
                convertUnitSetting.saveConvertUnit();
                Intent intent = new Intent();
                intent.setAction("setting.unit");
                sendBroadcast(intent);

            }
        });
    }




}
