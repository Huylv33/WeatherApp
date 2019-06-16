package com.project.mobile.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mobile.weatherapp.setting.BackgroundSetting;
import com.project.mobile.weatherapp.setting.ConvertUnitSetting;
import com.project.mobile.weatherapp.utils.ConvertUnit;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DialogHourly extends DialogFragment {
    public TextView mTextTemp;
    public TextView mTextPressure;
    public TextView texttime;
    public Context context;
    public ImageView mImage;
    public TextView txt_windSpeed;
    public TextView txt_humidity;
    public TextView description;
    private TextView pressure;
    private ImageView background;


    public DialogHourly() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_hourly_detail, container);
        Bundle args = getArguments();
        context = getContext().getApplicationContext();

        BackgroundSetting backgroundSetting = new BackgroundSetting(context);
        backgroundSetting.loadBackgroundSetting();
        RelativeLayout relativeLayout = v.findViewById(R.id.dialog_wallpaper_hourly);
//        relativeLayout.setBackgroundResource(backgroundSetting.backgroundId);
        background = (ImageView) v.findViewById(R.id.background_dialog);
        background.setImageResource(backgroundSetting.backgroundId);
        ConvertUnitSetting convertUnitSetting = new ConvertUnitSetting(context);
        convertUnitSetting.loadConvertUnit();
        ConvertUnit convertUnit = new ConvertUnit(convertUnitSetting.usingCelcius, convertUnitSetting.velocity);



        context = getActivity().getApplicationContext();
        texttime = (TextView) v.findViewById(R.id.textTimeHour);
        mTextTemp = (TextView) v.findViewById(R.id.textTempHour);
        mImage = (ImageView) v.findViewById(R.id.iconWeatherHour);
        txt_humidity = (TextView) v.findViewById(R.id.text_humidity_dialog);
        txt_windSpeed  = (TextView) v.findViewById(R.id.text_wind_dialog);
        description = (TextView) v.findViewById(R.id.text_weatherHour);
        pressure = (TextView) v.findViewById(R.id.text_pressure_dialog);

        NumberFormat format = new DecimalFormat("#0.0");
        String windSpeed = args.getString("wind");
        int humidity = args.getInt("humidity");
        String status = args.getString("status");


        String textTime = args.getString("textTime");
        String textTemp = args.getString("textTemp");
        float pre = args.getFloat("pressure");
        Integer icon = args.getInt("icon");
        texttime.setText(textTime);
        mTextTemp.setText(textTemp);
        mImage.setImageResource(icon);
        txt_windSpeed.setText(windSpeed);
        txt_humidity.setText(humidity + " %");
        description.setText(status);
        pressure.setText(format.format(pre) + " hpa");
        return v;
    }
}
