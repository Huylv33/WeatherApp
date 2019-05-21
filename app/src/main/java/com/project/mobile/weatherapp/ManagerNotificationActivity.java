package com.project.mobile.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ManagerNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_notification);
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
