package com.project.mobile.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ManagerLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_location);
    }

    public void onClick(int v) {
        switch (v) {
            case R.id.text_add_location: {
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
