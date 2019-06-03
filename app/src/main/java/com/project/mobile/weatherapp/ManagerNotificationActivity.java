package com.project.mobile.weatherapp;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.mobile.weatherapp.Setting.BackgroundSetting;
import com.project.mobile.weatherapp.Setting.NotificationSetting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ManagerNotificationActivity extends AppCompatActivity {

    public SwitchCompat weatherNoti, prepareDay;
    public NotificationSetting notificationSetting;
    private BackgroundSetting backgroundSetting;
    public LinearLayout linearLayout;
    public LinearLayout linearNoti;
    TextView txtTime;
    Calendar cal;
    Date hourFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notification);

        linearLayout = (LinearLayout) findViewById(R.id.activity_manager_notufication);
        backgroundSetting = new BackgroundSetting(this);
        backgroundSetting.loadBackgroundSetting();
        linearLayout.setBackgroundResource(backgroundSetting.backgroundId);

        txtTime=(TextView) findViewById(R.id.time_notification);
        linearNoti =  (LinearLayout) findViewById(R.id.select_time_notification);

        this.weatherNoti = (SwitchCompat)findViewById(R.id.switch_notification);
        this.prepareDay = (SwitchCompat) findViewById(R.id.switch_warning);
        notificationSetting = new NotificationSetting(this);
        notificationSetting.loadNotificationSetting();
        weatherNoti.setChecked(notificationSetting.notification);
        prepareDay.setChecked(notificationSetting.prepareDaily);

        getDefault();




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

        linearNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.select_time_notification: {
                        showTimePickerDialog();
                        break;
                    }
                }
            }
        });
    }






    public void showTimePickerDialog(){
        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String s = hourOfDay + ":" + minute;
                int hourTemp = hourOfDay;
                if(hourTemp > 12) hourTemp = hourTemp - 12;
                txtTime.setText(hourTemp + ":" + minute + (hourOfDay>12 ? " PM" : " AM"));
                txtTime.setTag(s);
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                hourFinish=cal.getTime();
            }
        };

        String s = txtTime.getTag()+"";
        String strArray[] = s.split(":");
        CharSequence charSequence = "Chọn giờ";
        int hour = Integer.parseInt(strArray[0]);
        int minutes = Integer.parseInt(strArray[1]);
        TimePickerDialog timeDialog = new TimePickerDialog(ManagerNotificationActivity.this,callback,hour,minutes,true);
        timeDialog.setTitle(charSequence);
        timeDialog.show();
    }

    public void getDefault(){
        cal = Calendar.getInstance();
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime = dft.format(cal.getTime());
        txtTime.setText(strTime);
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        txtTime.setTag(dft.format(cal.getTime()));
        hourFinish = cal.getTime();
    }

}
