package com.project.mobile.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.project.mobile.weatherapp.Setting.PrepareDaySetting;

public class PrepareDayActivity extends AppCompatActivity {

    public SwitchCompat umbbrela, coat, highTemp, coldTemp;
    public PrepareDaySetting prepareDaySetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_day);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        prepareDaySetting = new PrepareDaySetting(this);
        prepareDaySetting.loadPrepareDaySetting();
        umbbrela = (SwitchCompat) findViewById(R.id.enable_umbrella);
        coat = (SwitchCompat) findViewById(R.id.enable_coat);
        highTemp = (SwitchCompat) findViewById(R.id.enable_high_temp);
        coldTemp = (SwitchCompat) findViewById(R.id.enable_low_temp);

        umbbrela.setChecked(prepareDaySetting.umbbrela);
        coat.setChecked(prepareDaySetting.coat);
        highTemp.setChecked(prepareDaySetting.highTemp);
        coldTemp.setChecked(prepareDaySetting.coldTemp);


        umbbrela.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton,
                                                 boolean b) {
                        prepareDaySetting.umbbrela = umbbrela.isChecked();
                        prepareDaySetting.savePrepareDaySetting();
                    }
                });

        coat.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton,
                                                 boolean b) {
                        prepareDaySetting.coat = coat.isChecked();
                        prepareDaySetting.savePrepareDaySetting();
                    }
                });
        highTemp.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton,
                                                 boolean b) {
                        prepareDaySetting.highTemp = highTemp.isChecked();
                        prepareDaySetting.savePrepareDaySetting();
                    }
                });
        coldTemp.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton,
                                                 boolean b) {
                        prepareDaySetting.coldTemp = coldTemp.isChecked();
                        prepareDaySetting.savePrepareDaySetting();
                    }
                });
    }

    public void onRadioButtonClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.enable_umbrella:
                if (checked) {
//                    prepareDaySetting.coldTemp = coldTemp.isChecked();
//                    prepareDaySetting.savePrepareDaySetting();
                }

                    break;
            case R.id.enable_coat:
                if (checked) {
//                    prepareDaySetting.coat = coat.isChecked();
//                    prepareDaySetting.savePrepareDaySetting();
                }
                    // Ninjas rule
                    break;
            case R.id.enable_high_temp:
                if (checked) {
//                    prepareDaySetting.highTemp = highTemp.isChecked();
//                    prepareDaySetting.savePrepareDaySetting();
                }
                    // Ninjas rule
                    break;
            case R.id.enable_low_temp:
                if (checked) {
//                    prepareDaySetting.coldTemp = coldTemp.isChecked();
//                    prepareDaySetting.savePrepareDaySetting();
                }
                    // Ninjas rule
                    break;
        }
    }
    public void back(View v){
        finish();
    }

}
