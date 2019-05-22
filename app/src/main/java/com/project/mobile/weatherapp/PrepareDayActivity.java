package com.project.mobile.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioButton;

public class PrepareDayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_day);
    }

    public void onRadioButtonClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.enable_umbrella:
                if (checked)

                    break;
            case R.id.enable_coat:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.enable_high_temp:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.enable_low_temp:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}
