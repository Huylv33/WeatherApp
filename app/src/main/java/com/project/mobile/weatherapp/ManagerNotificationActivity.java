package com.project.mobile.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.project.mobile.weatherapp.Setting.BackgroundSetting;
import com.project.mobile.weatherapp.Setting.NotificationSetting;

public class ManagerNotificationActivity extends AppCompatActivity {

    public SwitchCompat weatherNoti, prepareDay;
    public NotificationSetting notificationSetting;
    private BackgroundSetting backgroundSetting;
    public LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notification);

        linearLayout = (LinearLayout) findViewById(R.id.activity_manager_notufication);
        backgroundSetting = new BackgroundSetting(this);
        backgroundSetting.loadBackgroundSetting();
        linearLayout.setBackgroundResource(backgroundSetting.backgroundId);

        this.weatherNoti = (SwitchCompat)findViewById(R.id.switch_notification);
        this.prepareDay = (SwitchCompat) findViewById(R.id.switch_warning);
        notificationSetting = new NotificationSetting(this);
        notificationSetting.loadNotificationSetting();
        weatherNoti.setChecked(notificationSetting.notification);
        prepareDay.setChecked(notificationSetting.prepareDaily);






        weatherNoti.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton,
                                                 boolean b) {
                        notificationSetting.notification = weatherNoti.isChecked();
                        notificationSetting.saveNotificationSetting();
                    }
                });
        prepareDay.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        notificationSetting.prepareDaily = prepareDay.isChecked();
                        notificationSetting.saveNotificationSetting();
                    }
                }
        );
    }



    public void onClick(int v) {
        switch (v) {
            case R.id.select_time_notification: {
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

}
