package com.project.mobile.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mobile.weatherapp.setting.BackgroundSetting;

public class ManageLocationActivity extends AppCompatActivity {

    public BackgroundSetting backgroundSetting;
    public RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_location);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        backgroundSetting = new BackgroundSetting(this);
        backgroundSetting.loadBackgroundSetting();
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_manager_location);
        relativeLayout.setBackgroundResource(backgroundSetting.backgroundId);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_add_location: {
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                Intent iAddLocation = new Intent(this, AddLocationActivity.class);
                startActivity(iAddLocation); 
                break;
            }
            case R.id.text_cur_location: {
                Intent currentLocation = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("usingLocation", true);
                currentLocation.putExtra("CurrentLocation", bundle);
                startActivity(currentLocation);
            }
        }
    }
    public void back(View v){
        finish();
    }
}
