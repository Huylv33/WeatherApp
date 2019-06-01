package com.project.mobile.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;
import com.project.mobile.weatherapp.Setting.ConvertUnit;

public class UnitSettingActivity extends AppCompatActivity {

    private ToggleSwitch temp;
    private ToggleSwitch distance ;
    private  ToggleSwitch time_format;
    private ToggleSwitch pre;
    private ConvertUnit convertUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_setting);
        ToggleSwitch temp = (ToggleSwitch) this.findViewById(R.id.switch_temperature);
        ToggleSwitch distance = (ToggleSwitch) this.findViewById(R.id.switch_distance);
        ToggleSwitch time_format = (ToggleSwitch) this.findViewById(R.id.switch_time_format);
        ToggleSwitch pre = (ToggleSwitch) this.findViewById(R.id.switch_precipitation);
        convertUnit = new ConvertUnit(this);
        convertUnit.loadConvertUnit();
        temp.setCheckedPosition(convertUnit.usingCelcius);
        distance.setCheckedPosition(convertUnit.velocity);
        time_format.setCheckedPosition(convertUnit.using12h);
        pre.setCheckedPosition(convertUnit.usingmm);


        time_format.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int position) {
                convertUnit.using12h = position;
                convertUnit.saveConvertUnit();

            }
        });

        distance.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                convertUnit.velocity = i;
                convertUnit.saveConvertUnit();
            }
        });

        pre.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                convertUnit.usingmm = i;
                convertUnit.saveConvertUnit();
            }
        });

        temp.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                convertUnit.usingCelcius = i;
                convertUnit.saveConvertUnit();
            }
        });
    }


}
