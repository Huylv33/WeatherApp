package com.project.mobile.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mobile.weatherapp.Setting.BackgroundSetting;
import com.project.mobile.weatherapp.Setting.PrepareDaySetting;

public class PrepareDayActivity extends AppCompatActivity {

    public BackgroundSetting backgroundSetting;

    public SwitchCompat umbbrela, coat, highTemp, coldTemp;
    public SeekBar seekUmbre, seekCoat, seekHigh, seekCold;
    public PrepareDaySetting prepareDaySetting;
    public LinearLayout linearLayout;
    public TextView txtUmbre, txtCoat, txtHigh, txtCold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_day);

        linearLayout = (LinearLayout) findViewById(R.id.activity_prepare_day);
        backgroundSetting = new BackgroundSetting(this);
        backgroundSetting.loadBackgroundSetting();
        linearLayout.setBackgroundResource(backgroundSetting.backgroundId);

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

        //thiet lap view cho seek bar
        seekUmbre = (SeekBar) findViewById(R.id.sb_umbrella);
        seekCoat = (SeekBar) findViewById(R.id.sb_coat);
        seekHigh = (SeekBar) findViewById(R.id.sb_high_temp);
        seekCold = (SeekBar) findViewById(R.id.sb_low_temp);
        txtUmbre = (TextView) findViewById(R.id.value_umbrella);
        txtCoat = (TextView) findViewById(R.id.value_coat_temp);
        txtHigh = (TextView) findViewById(R.id.value_high_temp);
        txtCold = (TextView) findViewById(R.id.value_low_temp);

        //thiet lap gia tri cho seekbar
        seekUmbre.setMax(100);
        seekUmbre.setMin(0);
        seekCoat.setMax(32);
        seekCoat.setMin(-1);
        seekHigh.setMax(50);
        seekHigh.setMin(30);
        seekCold.setMax(20);
        seekCold.setMin(-20);
        seekCoat.setProgress(prepareDaySetting.coat_seek);
        seekUmbre.setProgress(prepareDaySetting.umbbrela_seek);
        seekCold.setProgress(prepareDaySetting.coldTemp_seek);
        seekHigh.setProgress(prepareDaySetting.highTemp_seek);


        txtUmbre.setText(seekUmbre.getProgress() + "%");
        txtCoat.setText(seekCoat.getProgress() + "");
        txtHigh.setText(seekHigh.getProgress() + "");
        txtCold.setText(seekCold.getProgress() + "");

        //ham bat su kien nguoi dung keo tha seekbar de ra thong so
        seekUmbre.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            // Khi giá trị progress thay đổi.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                txtUmbre.setText(progressValue + "%" );
                prepareDaySetting.umbbrela_seek = progressValue;
                prepareDaySetting.savePrepareDaySetting();
                Log.i("umbre", "Changing seekbar's progress");
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            // Khi người dùng bắt đầu cử chỉ kéo thanh gạt.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("umbr", "Started tracking seekbar");
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            // Khi người dùng kết thúc cử chỉ kéo thanh gạt.
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtUmbre.setText(progress + "%");
                Log.i("umb", "Stopped tracking seekbar");
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();

            }
        });

        seekCoat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            // Khi giá trị progress thay đổi.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                txtCoat.setText(progressValue + "" );
                prepareDaySetting.coat_seek = progressValue;
                prepareDaySetting.savePrepareDaySetting();
                Log.i("umbre", "Changing seekbar's progress");
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            // Khi người dùng bắt đầu cử chỉ kéo thanh gạt.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("umbr", "Started tracking seekbar");
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            // Khi người dùng kết thúc cử chỉ kéo thanh gạt.
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtCoat.setText(progress + "");
                Log.i("umb", "Stopped tracking seekbar");
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();

            }
        });

        seekHigh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            // Khi giá trị progress thay đổi.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                txtHigh.setText(progressValue + "" );
                prepareDaySetting.highTemp_seek = progressValue;
                prepareDaySetting.savePrepareDaySetting();
                Log.i("umbre", "Changing seekbar's progress");
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            // Khi người dùng bắt đầu cử chỉ kéo thanh gạt.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("umbr", "Started tracking seekbar");
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            // Khi người dùng kết thúc cử chỉ kéo thanh gạt.
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtHigh.setText(progress + "");
                Log.i("umb", "Stopped tracking seekbar");
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();

            }
        });

        seekCold.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            // Khi giá trị progress thay đổi.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress = progressValue;
                txtCold.setText(progressValue + "" );

                Log.i("umbre", "Changing seekbar's progress");
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            // Khi người dùng bắt đầu cử chỉ kéo thanh gạt.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("umbr", "Started tracking seekbar");
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            // Khi người dùng kết thúc cử chỉ kéo thanh gạt.
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtCold.setText(progress + "");
                Log.i("umb", "Stopped tracking seekbar");
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();

            }
        });
    }



 /*   public void onRadioButtonClick(View view) {
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
    } */
}
